package com.dereekb.gae.test.extras.gen.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.dereekb.gae.test.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.test.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.test.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.test.extras.gen.utility.GenFile;
import com.dereekb.gae.test.extras.gen.utility.GenFolder;
import com.dereekb.gae.test.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.test.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.data.ValueUtility;

/**
 * {@link AbstractConfigurationGenerator}
 *
 * @author dereekb
 *
 */
public abstract class AbstractModelConfigurationGenerator extends AbstractConfigurationGenerator {

	private String resultsFolderName = "results";

	private boolean splitByModel = false;
	private boolean splitByGroup = false;
	private boolean makeImportFile = true;

	public AbstractModelConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
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

	public boolean isSplitByGroup() {
		return this.splitByGroup;
	}

	public void setSplitByGroup(boolean splitByGroup) {
		this.splitByGroup = splitByGroup;
	}

	public boolean isMakeImportFile() {
		return this.makeImportFile;
	}

	public void setMakeImportFile(boolean makeImportFile) {
		this.makeImportFile = makeImportFile;
	}

	// MARK: AbstractConfigurationGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		return this.makeModelClientConfigurations();
	}

	private final GenFolderImpl makeModelClientConfigurations() {
		if (this.isSplitByGroup()) {
			return this.makeModelClientConfigurationsSplitByGroup();
		} else {
			return this.makeModelClientConfigurationsTogether();
		}
	}

	public GenFolderImpl makeModelClientConfigurationsSplitByGroup() {
		GenFolderImpl folder = new GenFolderImpl(this.resultsFolderName);

		for (AppModelConfigurationGroup groupConfig : this.getAppConfig().getModelConfigurations()) {
			folder.addFolder(this.makeModelClientConfigurationsForModels(groupConfig));
		}

		return folder;
	}

	public GenFolderImpl makeModelClientConfigurationsTogether() {
		GenFolderImpl folder = new GenFolderImpl(this.resultsFolderName);

		for (AppModelConfigurationGroup group : this.getAppConfig().getModelConfigurations()) {
			folder.merge(this.makeModelClientConfigurationsForModels(group.getModelConfigurations()));
		}

		return folder;
	}

	public GenFolderImpl makeModelClientConfigurationsForModels(AppModelConfigurationGroup group) {
		return this.makeModelClientConfigurationsForModels(group.getGroupName(), group.getModelConfigurations());
	}

	public GenFolderImpl makeModelClientConfigurationsForModels(String folderName,
	                                                            List<AppModelConfiguration> modelConfigs) {
		GenFolderImpl folder = new GenFolderImpl(ValueUtility.defaultTo(folderName, "default"));
		List<GenFolder> results = this.makeModelClientConfigurationsForModels(modelConfigs);
		folder.addFolders(results);
		return folder;
	}

	public List<GenFolder> makeModelClientConfigurationsForModels(List<AppModelConfiguration> modelConfigs) {
		List<GenFolder> results = new ArrayList<GenFolder>();

		for (AppModelConfiguration modelConfig : modelConfigs) {
			GenFolder result = this.makeModelClientConfiguration(modelConfig);
			results.add(result);
		}

		if (!this.splitByModel) {
			GenFolderImpl folder = new GenFolderImpl("merge");
			folder.merge(results);
			return ListUtility.wrap(folder);
		}

		return results;
	}

	public GenFolder makeModelClientConfiguration(AppModelConfiguration modelConfig) {
		GenFolderImpl folder = new GenFolderImpl(modelConfig.getModelType());
		this.makeModelClientConfiguration(folder, modelConfig);
		return folder;
	}

	/**
	 * Override this if the implementation should make a folder of configuration
	 * per model.
	 */
	public void makeModelClientConfiguration(GenFolderImpl modelResultsFolder,
	                                         AppModelConfiguration modelConfig) {
		GenFile file = this.makeModelClientConfigurationFile(modelConfig);
		modelResultsFolder.addFile(file);
	}

	/**
	 * Override this for convenience if the implementation only creates a single
	 * file that doesn't return XML.
	 */
	public GenFile makeModelClientConfigurationFile(AppModelConfiguration modelConfig)
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
	public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Override in subclass to use.");
	}

}
