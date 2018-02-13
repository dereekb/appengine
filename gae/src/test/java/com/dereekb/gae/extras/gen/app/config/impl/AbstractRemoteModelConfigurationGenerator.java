package com.dereekb.gae.extras.gen.app.config.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * {@link AbstractModelConfigurationGenerator} extension for building
 * configuration based on whether the model is local or remote.
 *
 * @author dereekb
 *
 */
public abstract class AbstractRemoteModelConfigurationGenerator extends AbstractModelConfigurationGenerator {

	private boolean splitByRemote = true;
	private boolean makeSplitByRemoteImportFiles = true;

	private String localFolderName = "local";
	private String remoteFolderName = "remote";

	public AbstractRemoteModelConfigurationGenerator(AppConfiguration appConfig) {
		this(appConfig, null);
	}

	public AbstractRemoteModelConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setSplitByGroup(false);
	}

	public boolean isSplitByRemote() {
		return this.splitByRemote;
	}

	public void setSplitByRemote(boolean splitByRemote) {
		this.splitByRemote = splitByRemote;
	}

	public String getLocalFolderName() {
		return this.localFolderName;
	}

	public void setLocalFolderName(String localFolderName) {
		if (localFolderName == null) {
			throw new IllegalArgumentException("localFolderName cannot be null.");
		}

		this.localFolderName = localFolderName;
	}

	public String getRemoteFolderName() {
		return this.remoteFolderName;
	}

	public void setRemoteFolderName(String remoteFolderName) {
		if (remoteFolderName == null) {
			throw new IllegalArgumentException("remoteFolderName cannot be null.");
		}

		this.remoteFolderName = remoteFolderName;
	}

	@Override
	public ModelClientConfigurationGenerator makeGeneratorForConfiguration() {
		if (this.splitByRemote) {
			return new SplitByRemoteModelClientConfigurationGenerator();
		} else {
			return super.makeGeneratorForConfiguration();
		}
	}

	@Override
	public final SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		if (modelConfig.isLocalModel()) {
			return this.makeLocalXMLModelClientConfigurationFile(modelConfig);
		} else {
			return this.makeRemoteXMLModelClientConfigurationFile(modelConfig);
		}
	}

	public SpringBeansXMLBuilder makeLocalXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		return super.makeXMLModelClientConfigurationFile(modelConfig);
	}

	public SpringBeansXMLBuilder makeRemoteXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		return super.makeXMLModelClientConfigurationFile(modelConfig);
	}

	// MARK: ModelClientConfigurationGenerator
	public class SplitByRemoteModelClientConfigurationGenerator
	        implements ModelClientConfigurationGenerator {

		@Override
		public GenFolderImpl makeModelConfigurations(List<AppModelConfigurationGroup> groups) {
			GenFolderImpl folder = new GenFolderImpl("models");

			LocalModelFilter filter = new LocalModelFilter();

			List<AppModelConfiguration> localModels = new ArrayList<AppModelConfiguration>();
			List<AppModelConfiguration> remoteModels = new ArrayList<AppModelConfiguration>();

			// Break into local/remote
			for (AppModelConfigurationGroup groupConfig : groups) {
				FilterResults<AppModelConfiguration> results = filter
				        .filterObjects(groupConfig.getModelConfigurations());
				localModels.addAll(results.getPassingObjects());
				remoteModels.addAll(results.getFailingObjects());
			}

			GenFolderImpl localFolder = AbstractRemoteModelConfigurationGenerator.this
			        .makeModelClientConfigurationsForModels(
			                AbstractRemoteModelConfigurationGenerator.this.localFolderName, localModels);
			localFolder.flatten();

			GenFolderImpl remoteFolder = AbstractRemoteModelConfigurationGenerator.this
			        .makeModelClientConfigurationsForModels(
			                AbstractRemoteModelConfigurationGenerator.this.remoteFolderName, remoteModels);
			remoteFolder.flatten();


			folder.addFolder(localFolder);
			folder.addFolder(remoteFolder);

			if (AbstractRemoteModelConfigurationGenerator.this.makeSplitByRemoteImportFiles) {
				localFolder.addFile(makeImportFile(localFolder));
				remoteFolder.addFile(makeImportFile(remoteFolder));
			}

			return folder;
		}

	}

	public static class LocalModelFilter extends AbstractFilter<AppModelConfiguration> {

		@Override
		public FilterResult filterObject(AppModelConfiguration object) {
			return FilterResult.valueOf(object.isLocalModel());
		}

	}

}