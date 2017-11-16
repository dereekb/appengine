package com.dereekb.gae.test.server.auth.security.model.context;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextCrudRole;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRoleSet;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextRoleSetEncoderDecoder;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoder;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoderDecoderEntry;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.LoginTokenModelContextSetEncoderDecoderEntryImpl;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.LoginTokenModelContextSetEncoderDecoderImpl;
import com.dereekb.gae.server.auth.security.model.context.impl.LoginTokenModelContextBuilder;
import com.dereekb.gae.server.auth.security.model.context.impl.LoginTokenModelContextRoleSetImpl;
import com.dereekb.gae.server.auth.security.model.context.impl.LoginTokenModelContextRoleSetUtility;
import com.dereekb.gae.server.auth.security.model.context.impl.LoginTokenModelContextSetImpl;
import com.dereekb.gae.server.auth.security.model.context.impl.LoginTokenTypedModelContextSetImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link LoginTokenModelContextDencoder} tests.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextDencoderTests {

	@Test
	public void testEncodeEmptySet() {
		LoginTokenModelContextSetEncoderDecoder dencoder = makeDencoder();

		LoginTokenModelContextSetImpl contextSet = new LoginTokenModelContextSetImpl();
		EncodedLoginTokenModelContextSet encodedSet = dencoder.encodeSet(contextSet);

		Assert.assertTrue(encodedSet.getEncodedModelContextTypes().isEmpty());
	}

	@Test
	public void testEncodeAndDecodeSingleSet() {
		LoginTokenModelContextSetEncoderDecoder dencoder = makeDencoder();

		String aType = "a";
		Integer aTypeCode = 0;

		// Make Context A
		LoginTokenModelContextRoleSet aRoles = new LoginTokenModelContextRoleSetImpl(
		        LoginTokenModelContextCrudRole.READ);
		LoginTokenModelContextBuilder.Builder aBuilder = LoginTokenModelContextBuilder.make(aType, aRoles);
		List<LoginTokenModelContext> aContexts = aBuilder
		        .make(ListUtility.toList(new ModelKey(1L), new ModelKey(2L), new ModelKey(3L)));
		LoginTokenTypedModelContextSetImpl a = new LoginTokenTypedModelContextSetImpl(aType, aContexts);

		// Make Context Set From A
		LoginTokenModelContextSetImpl contextSet = new LoginTokenModelContextSetImpl();
		contextSet.add(a);

		// Encode
		EncodedLoginTokenModelContextSet encodedSet = dencoder.encodeSet(contextSet);
		Assert.assertFalse(encodedSet.getEncodedModelContextTypes().isEmpty());
		Assert.assertTrue(encodedSet.getEncodedModelContextTypes().contains(aTypeCode));
		Assert.assertNotNull(encodedSet.getEncodedModelTypeContext(aTypeCode));

		// Decode
		LoginTokenModelContextSet decoded = dencoder.decodeSet(encodedSet);

		LoginTokenTypedModelContextSet decodedContextA = decoded.getContextsForType(aType);

		Assert.assertNotNull(decodedContextA);
		Assert.assertTrue(decodedContextA.getModelType().equals(aType));
		Assert.assertFalse(decodedContextA.getContexts().isEmpty());
	}

	@Test
	public void testEncodeAndDecodeSets() {
		LoginTokenModelContextSetEncoderDecoder dencoder = makeDencoder();

		// Make Context A
		String aType = "a";
		Integer aTypeCode = 0;

		LoginTokenModelContextRoleSet aRoles = new LoginTokenModelContextRoleSetImpl(
		        LoginTokenModelContextCrudRole.READ);
		LoginTokenModelContextBuilder.Builder aBuilder = LoginTokenModelContextBuilder.make(aType, aRoles);
		List<LoginTokenModelContext> aContexts = aBuilder
		        .make(ListUtility.toList(new ModelKey(1L), new ModelKey(2L), new ModelKey(3L)));
		LoginTokenTypedModelContextSetImpl a = new LoginTokenTypedModelContextSetImpl(aType, aContexts);

		// Make Context B
		String bType = "b";
		Integer bTypeCode = 1;

		LoginTokenModelContextRoleSet bRoles = new LoginTokenModelContextRoleSetImpl(
		        LoginTokenModelContextCrudRole.READ);
		LoginTokenModelContextBuilder.Builder bBuilder = LoginTokenModelContextBuilder.make(bType, bRoles);
		List<LoginTokenModelContext> bContexts = bBuilder
		        .make(ListUtility.toList(new ModelKey(1L), new ModelKey(2L), new ModelKey(3L)));
		LoginTokenTypedModelContextSetImpl b = new LoginTokenTypedModelContextSetImpl(bType, bContexts);

		// Make Context Set From A and B
		LoginTokenModelContextSetImpl contextSet = new LoginTokenModelContextSetImpl();
		contextSet.add(a);
		contextSet.add(b);

		// Encode
		EncodedLoginTokenModelContextSet encodedSet = dencoder.encodeSet(contextSet);

		Assert.assertFalse(encodedSet.getEncodedModelContextTypes().isEmpty());
		Assert.assertTrue(encodedSet.getEncodedModelContextTypes().contains(aTypeCode));
		Assert.assertTrue(encodedSet.getEncodedModelContextTypes().contains(bTypeCode));
		Assert.assertNotNull(encodedSet.getEncodedModelTypeContext(aTypeCode));
		Assert.assertNotNull(encodedSet.getEncodedModelTypeContext(bTypeCode));

		// Decode
		LoginTokenModelContextSet decoded = dencoder.decodeSet(encodedSet);

		LoginTokenTypedModelContextSet decodedContextA = decoded.getContextsForType(aType);
		LoginTokenTypedModelContextSet decodedContextB = decoded.getContextsForType(bType);

		Assert.assertNotNull(decodedContextA);
		Assert.assertTrue(decodedContextA.getModelType().equals(aType));
		Assert.assertFalse(decodedContextA.getContexts().isEmpty());

		Assert.assertNotNull(decodedContextB);
		Assert.assertTrue(decodedContextB.getModelType().equals(bType));
		Assert.assertFalse(decodedContextB.getContexts().isEmpty());
	}

	// MARK: Utilities
	protected static LoginTokenModelContextSetEncoderDecoder makeDencoder() {

		LoginTokenModelContextSetEncoderDecoderEntry a = makeCrudEntry(0, "a", ModelKeyType.NUMBER);
		LoginTokenModelContextSetEncoderDecoderEntry b = makeCrudEntry(1, "b", ModelKeyType.NUMBER);
		LoginTokenModelContextSetEncoderDecoderEntry c = makeCrudEntry(2, "c", ModelKeyType.NAME);

		List<LoginTokenModelContextSetEncoderDecoderEntry> delegates = ListUtility.toList(a, b, c);

		LoginTokenModelContextSetEncoderDecoderImpl dencoder = new LoginTokenModelContextSetEncoderDecoderImpl(
		        delegates);
		return dencoder;
	}

	protected static LoginTokenModelContextSetEncoderDecoderEntry makeCrudEntry(Integer code,
	                                                                            String modelType,
	                                                                            ModelKeyType keyType) {
		LoginTokenModelContextRoleSetEncoderDecoder dencoder = LoginTokenModelContextRoleSetUtility.makeCrudDencoder();
		LoginTokenModelContextSetEncoderDecoderEntry entry = new LoginTokenModelContextSetEncoderDecoderEntryImpl(code,
		        modelType, ModelKeyType.NUMBER, dencoder);
		return entry;
	}

}