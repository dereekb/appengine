package com.dereekb.gae.extras.gen.app.config.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.data.ValueUtility;

/**
 * {@link AbstractConfigurationFileGenerator} extension for files generated by
 * model.
 *
 * @author dereekb
 *
 */
public abstract class AbstractModelConfigurationGenerator extends AbstractConfigurationFileGenerator {

	private String resultsFolderName = "results";

	private boolean splitByModel = false;
	private boolean splitByGroup = false;
	private boolean ignoreLocal = false;
	private boolean ignoreRemote = true;

	public AbstractModelConfigurationGenerator(AppConfiguration appConfig) {
		this(appConfig, null);
	}

	public AbstractModelConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
	}

	public String getResultsFolderName() {
		return this.resultsFolderName;
	}

	public void setResultsFolderName(String resultsFolderName) {
		if (resultsFolderName == null) {
			throw new IllegalArgumentException("resultsFolderName cannot be null.");
		}

		this.resultsFolderName = resultsFolderName;
	}

	public boolean isSplitByModel() {
		return this.splitByModel;
	}

	public void setSplitByModel(boolean splitByModel) {
		this.splitByModel = splitByModel;
	}

	public boolean isSplitByGroup() {
		return this.splitByGroup;
	}

	public void setSplitByGroup(boolean splitByGroup) {
		this.splitByGroup = splitByGroup;
	}

	public boolean isIgnoreLocal() {
		return this.ignoreLocal;
	}

	public void setIgnoreLocal(boolean ignoreLocal) {
		this.ignoreLocal = ignoreLocal;
	}

	public boolean isIgnoreRemote() {
		return this.ignoreRemote;
	}

	public void setIgnoreRemote(boolean ignoreRemote) {
		this.ignoreRemote = ignoreRemote;
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		return this.makeModelClientConfigurations();
	}

	private final GenFolderImpl makeModelClientConfigurations() {
		return this.makeGeneratorForConfiguration()
		        .makeModelConfigurations(this.getAppConfig().getModelConfigurations());
	}

	public ModelClientConfigurationGenerator makeGeneratorForConfiguration() {
		if (this.isSplitByGroup()) {
			return new SplitByGroupModelClientConfigurationGenerator();
		} else {
			return new TogetherModelClientConfigurationGenerator();
		}
	}

	public GenFolderImpl makeModelClientConfigurationsForModels(LocalModelConfigurationGroup group) {
		return this.makeModelClientConfigurationsForModels(group.getGroupName(), group.getModelConfigurations());
	}

	public GenFolderImpl makeModelClientConfigurationsForModels(String folderName,
	                                                            List<LocalModelConfiguration> modelConfigs) {
		GenFolderImpl folder = new GenFolderImpl(ValueUtility.defaultTo(folderName, "default"));
		List<GenFolder> results = this.makeModelClientConfigurationsForModels(modelConfigs);
		folder.addFolders(results);
		return folder;
	}

	public List<GenFolder> makeModelClientConfigurationsForModels(List<LocalModelConfiguration> modelConfigs) {
		List<GenFolder> results = new ArrayList<GenFolder>();

		for (LocalModelConfiguration modelConfig : modelConfigs) {
			if (this.shouldMakeModelConfiguration(modelConfig)) {
				GenFolder result = this.makeModelClientConfiguration(modelConfig);
				results.add(result);
			}
		}

		if (!this.splitByModel) {
			GenFolderImpl folder = new GenFolderImpl("merge");
			folder.merge(results);
			return ListUtility.wrap(folder);
		}

		return results;
	}

	protected boolean shouldMakeModelConfiguration(LocalModelConfiguration modelConfig) {
		boolean local = modelConfig.isLocalModel();

		if (local) {
			return !this.ignoreLocal;
		} else {
			return !this.ignoreRemote;
		}
	}

	public GenFolderImpl makeModelClientConfiguration(LocalModelConfiguration modelConfig) {
		GenFolderImpl folder = new GenFolderImpl(modelConfig.getModelType());
		this.makeModelClientConfiguration(folder, modelConfig);
		return folder;
	}

	/**
	 * Override this if the implementation should make a folder of configuration
	 * per model.
	 */
	public void makeModelClientConfiguration(GenFolderImpl modelResultsFolder,
	                                         LocalModelConfiguration modelConfig) {
		GenFile file = this.makeModelClientConfigurationFile(modelConfig);
		modelResultsFolder.addFile(file);
	}

	/**
	 * Override this for convenience if the implementation only creates a single
	 * file that doesn't return XML.
	 */
	public GenFile makeModelClientConfigurationFile(LocalModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = this.makeXMLModelClientConfigurationFile(modelConfig);
		String fileName = modelConfig.getModelType().toLowerCase();
		return this.makeFileWithXML(fileName, builder);
	}

	/**
	 * Override this for convenience if the implementation only creates a single
	 * file.
	 *
	 * @throws UnsupportedOperationException
	 *             thrown by default if not overridden.
	 */
	public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Override in subclass to use.");
	}

	// MARK: Generator
	public interface ModelClientConfigurationGenerator {

		public GenFolderImpl makeModelConfigurations(List<LocalModelConfigurationGroup> groups);

	}

	public class SplitByGroupModelClientConfigurationGenerator
	        implements ModelClientConfigurationGenerator {

		@Override
		public GenFolderImpl makeModelConfigurations(List<LocalModelConfigurationGroup> groups) {
			GenFolderImpl folder = new GenFolderImpl(AbstractModelConfigurationGenerator.this.resultsFolderName);

			for (LocalModelConfigurationGroup groupConfig : groups) {
				folder.addFolder(makeModelClientConfigurationsForModels(groupConfig));
			}

			return folder;
		}

	}

	public class TogetherModelClientConfigurationGenerator
	        implements ModelClientConfigurationGenerator {

		@Override
		public GenFolderImpl makeModelConfigurations(List<LocalModelConfigurationGroup> groups) {
			GenFolderImpl folder = new GenFolderImpl(AbstractModelConfigurationGenerator.this.resultsFolderName);

			for (LocalModelConfigurationGroup group : groups) {
				folder.merge(makeModelClientConfigurationsForModels(group.getModelConfigurations()));
			}

			return folder;
		}

	}

	// MARK: Utility
	protected List<LocalModelConfiguration> getAllApplicableConfigurations() {
		return this.getAllApplicableConfigurations(this.getAppConfig().getModelConfigurations());
	}

	protected List<LocalModelConfiguration> getAllApplicableConfigurations(List<LocalModelConfigurationGroup> groups) {
		List<LocalModelConfiguration> configs = new ArrayList<LocalModelConfiguration>();

		for (LocalModelConfigurationGroup groupConfig : groups) {
			for (LocalModelConfiguration modelConfig : groupConfig.getModelConfigurations()) {
				if (this.shouldMakeModelConfiguration(modelConfig)) {
					configs.add(modelConfig);
				}
			}
		}

		return configs;
	}

	protected List<LocalModelConfiguration> getAllLocalConfigurations() {
		return this.getAllLocalConfigurations(this.getAppConfig().getModelConfigurations());
	}

	protected List<LocalModelConfiguration> getAllLocalConfigurations(List<LocalModelConfigurationGroup> groups) {
		List<LocalModelConfiguration> configs = new ArrayList<LocalModelConfiguration>();

		for (LocalModelConfigurationGroup groupConfig : groups) {
			for (LocalModelConfiguration modelConfig : groupConfig.getModelConfigurations()) {
				if (modelConfig.isLocalModel()) {
					configs.add(modelConfig);
				}
			}
		}

		return configs;
	}

}
