package com.dereekb.gae.extras.gen.app.config.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AbstractModelConfigurationGenerator} extension for building
 * configuration based on whether the model is local or remote.
 *
 * @author dereekb
 *
 */
@Deprecated
public abstract class AbstractRemoteModelConfigurationGenerator extends AbstractModelConfigurationGenerator {

	private boolean ignoreRemote = false;
	private boolean splitByRemote = true;
	private boolean makeSplitByRemoteImportFiles = true;

	private String modelsFolderName = "models";
	private String localFolderName = "local";
	private String remoteFolderName = "remote";

	public AbstractRemoteModelConfigurationGenerator(AppConfiguration appConfig) {
		this(appConfig, null);
	}

	public AbstractRemoteModelConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setSplitByGroup(false);
		this.setIgnoreRemote(false);
	}

	@Override
	public boolean isIgnoreRemote() {
		return this.ignoreRemote;
	}

	@Override
	public void setIgnoreRemote(boolean ignoreRemote) {
		this.ignoreRemote = ignoreRemote;
	}

	@Override
	public boolean isSplitByRemote() {
		return this.splitByRemote;
	}

	@Override
	public void setSplitByRemote(boolean splitByRemote) {
		this.splitByRemote = splitByRemote;
	}

	public boolean isMakeSplitByRemoteImportFiles() {
		return this.makeSplitByRemoteImportFiles;
	}

	public void setMakeSplitByRemoteImportFiles(boolean makeSplitByRemoteImportFiles) {
		this.makeSplitByRemoteImportFiles = makeSplitByRemoteImportFiles;
	}

	public String getModelsFolderName() {
		return this.modelsFolderName;
	}

	public void setModelsFolderName(String modelsFolderName) {
		if (modelsFolderName == null) {
			throw new IllegalArgumentException("modelsFolderName cannot be null.");
		}

		this.modelsFolderName = modelsFolderName;
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
	public final SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		if (modelConfig.isLocalModel()) {
			return this.makeLocalXMLModelClientConfigurationFile(modelConfig);
		} else {
			return this.makeRemoteXMLModelClientConfigurationFile(modelConfig);
		}
	}

	public SpringBeansXMLBuilder makeLocalXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		return super.makeXMLModelClientConfigurationFile(modelConfig);
	}

	public SpringBeansXMLBuilder makeRemoteXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		return super.makeXMLModelClientConfigurationFile(modelConfig);
	}

	// MARK: ModelClientConfigurationGenerator
	public class SplitByRemoteModelClientConfigurationGenerator
	        implements ModelClientConfigurationGenerator {

		@Override
		public GenFolderImpl makeLocalModelConfigurations(List<LocalModelConfigurationGroup> groups) {
			GenFolderImpl folder = new GenFolderImpl(AbstractRemoteModelConfigurationGenerator.this.modelsFolderName);

			List<LocalModelConfiguration> localModels = new ArrayList<LocalModelConfiguration>();
			List<RemoteModelConfiguration> remoteModels = new ArrayList<RemoteModelConfiguration>();

			// Break into local/remote
			for (LocalModelConfigurationGroup groupConfig : groups) {
				localModels.addAll(groupConfig.getModelConfigurations());
			}

			// Local Folder
			GenFolderImpl localFolder = null;

			if (AbstractRemoteModelConfigurationGenerator.this.isIgnoreLocal() == false) {
				localFolder = AbstractRemoteModelConfigurationGenerator.this.makeModelClientConfigurationsForModels(
				        AbstractRemoteModelConfigurationGenerator.this.localFolderName, localModels);

				if (AbstractRemoteModelConfigurationGenerator.this.isSplitByModel() == false) {
					localFolder.flatten();
				}

				folder.addFolder(localFolder);
			}

			// Remote Folder
			GenFolderImpl remoteFolder = null;

			if (AbstractRemoteModelConfigurationGenerator.this.isIgnoreRemote() == false) {
				remoteFolder = AbstractRemoteModelConfigurationGenerator.this.makeRemoteModelClientConfigurationsForModels(
				        AbstractRemoteModelConfigurationGenerator.this.remoteFolderName, remoteModels);

				if (AbstractRemoteModelConfigurationGenerator.this.isSplitByModel() == false) {
					remoteFolder.flatten();
				}

				folder.addFolder(remoteFolder);
			}

			if (AbstractRemoteModelConfigurationGenerator.this.makeSplitByRemoteImportFiles) {
				if (localFolder != null) {
					localFolder.addFile(makeImportFile(localFolder));
				}

				if (remoteFolder != null) {
					remoteFolder.addFile(makeImportFile(remoteFolder));
				}
			}

			return folder;
		}

	}

}
