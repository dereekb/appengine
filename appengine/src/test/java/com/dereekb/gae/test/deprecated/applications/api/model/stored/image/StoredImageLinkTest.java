package com.dereekb.gae.test.applications.api.model.stored.image.d;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.links.deprecated.deleter.LinkDeleterServiceRequest;
import com.dereekb.gae.model.extension.links.deprecated.deleter.impl.LinkDeleterServiceRequestImpl;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.deprecated.applications.api.model.extension.links.AbstractLinkServiceTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

/**
 * 
 * @author dereekb
 * 
 * @deprecated Replace with client tests.
 */
@Disabled
@Deprecated
public class StoredImageLinkTest extends AbstractLinkServiceTest {

	@Autowired
	@Qualifier("storedImageStoredBlobLinkName")
	private String storedImageStoredBlobLinkName;

	// MARK: Components
	@Autowired
	@Qualifier("storedImageType")
	private String storedImageLinkType;

	@Autowired
	@Qualifier("storedImageSetType")
	private String storedImageSetLinkType;

	@Autowired
	@Qualifier("storedBlobType")
	private String storedBlobLinkType;

	@Autowired
	@Qualifier("storedBlobRegistry")
	private ObjectifyRegistry<StoredBlob> storedBlobRegistry;

	@Autowired
	@Qualifier("storedImageRegistry")
	private ObjectifyRegistry<StoredImage> storedImageRegistry;

	@Autowired
	@Qualifier("storedImageSetRegistry")
	private ObjectifyRegistry<StoredImageSet> storedImageSetRegistry;

	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	private TestModelGenerator<StoredImage> storedImageGenerator;

	@Autowired
	@Qualifier("storedImageSetTestModelGenerator")
	private TestModelGenerator<StoredImageSet> storedImageSetGenerator;

	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	private TestModelGenerator<StoredBlob> storedBlobGenerator;

	@Disabled
	@Deprecated
	@Test
	public void testLinkingToStoredBlob() {
		StoredBlob storedBlob = this.storedBlobGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		// Clear any generated links
		storedBlob.setDescriptor(null);
		this.storedBlobRegistry.update(storedBlob);

		storedImage.setStoredBlob(null);
		this.storedImageRegistry.update(storedImage);

		// Start Test Linking
		this.linkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageStoredBlobLinkName,
		        storedBlob.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		storedBlob = this.storedBlobRegistry.get(storedBlob);

		assertTrue(storedBlob.getObjectifyKey().equals(storedImage.getStoredBlob()));
		assertTrue(storedImage.equals(storedBlob.getDescriptor()));

		// Test Unlinking
		this.unlinkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageStoredBlobLinkName,
		        storedBlob.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		storedBlob = this.storedBlobRegistry.get(storedBlob);

		assertFalse(storedBlob.getObjectifyKey().equals(storedImage.getStoredBlob()));
		assertFalse(storedImage.equals(storedBlob.getDescriptor()));
	}

	@Disabled
	@Test
	public void testLinkDeleter() {
		StoredImage storedImage = this.storedImageGenerator.generate();
		StoredBlob storedBlob = this.storedBlobGenerator.generate();

		// Clear all generated links
		storedBlob.setDescriptor(null);
		this.storedBlobRegistry.update(storedBlob);

		storedImage.setStoredBlob(null);
		this.storedImageRegistry.update(storedImage);

		// Link Together
		this.linkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageStoredBlobLinkName,
		        storedBlob.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);

		// Delete Links
		LinkDeleterServiceRequest request = new LinkDeleterServiceRequestImpl(this.storedImageLinkType,
		        storedImage.getModelKey());
		this.linkDeleterService.deleteLinks(request);

		storedBlob = this.storedBlobRegistry.get(storedBlob);
		storedImage = this.storedImageRegistry.get(storedImage);

		// Check Not Linked. Deletable Items should be deleted via taskqueue.
		assertFalse(storedBlob.getObjectifyKey().equals(storedImage.getStoredBlob()));
		assertFalse(storedImage.equals(storedBlob.getDescriptor()));
	}

}
