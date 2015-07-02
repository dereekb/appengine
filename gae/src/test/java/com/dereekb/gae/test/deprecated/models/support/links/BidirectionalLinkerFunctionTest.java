package com.thevisitcompany.gae.test.deprecated.models.support.links;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.thevisitcompany.gae.model.extension.links.functions.BidirectionalLinkerFunction;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;
import com.thevisitcompany.gae.model.extension.links.functions.LinksPair;

@RunWith(JUnit4.class)
public class BidirectionalLinkerFunctionTest extends AbstractLinkerFunctionTests {

	@Test
	public void testSingularBidirectionalLinking() {

		LinkerObjectGetterSetter getterSetter = new LinkerObjectGetterSetter();
		LinkerObjectLinkHandler handler = new LinkerObjectLinkHandler();

		BidirectionalLinkerFunction<LinkerObject, LinkerObject, Long> function = new BidirectionalLinkerFunction<LinkerObject, LinkerObject, Long>();
		function.setSecondaryGetter(getterSetter);
		function.setSecondarySetter(getterSetter);

		function.setPrimaryHandler(handler);
		function.setSecondaryHandler(handler);

		function.setTypeDelegate(handler);

		List<Long> linkIds = new ArrayList<Long>();
		linkIds.add(2L);

		Long objectAIdentifier = 1L;
		LinkerObject objectA = new LinkerObject(objectAIdentifier);
		LinksPair<LinkerObject, Long> pairA = new LinksPair<LinkerObject, Long>(objectA, linkIds, "a", LinksAction.LINK);

		function.addObject(pairA);
		boolean success = function.run();

		Assert.assertTrue(success);
		List<LinkerObject> savedObjects = getterSetter.getSavedObjects();
		Assert.assertTrue(savedObjects.size() == 1);

		LinkerObject object = savedObjects.get(0);
		Set<Long> bSet = object.getSetB();

		Assert.assertTrue(bSet.contains(objectAIdentifier));
		Assert.assertTrue(objectA.getSetA().contains(object.getId()));
	}

	@Test
	public void testMultipleBidirectionalLinking() {

		LinkerObjectGetterSetter getterSetter = new LinkerObjectGetterSetter();
		LinkerObjectLinkHandler handler = new LinkerObjectLinkHandler();

		BidirectionalLinkerFunction<LinkerObject, LinkerObject, Long> function = new BidirectionalLinkerFunction<LinkerObject, LinkerObject, Long>();
		function.setSecondaryGetter(getterSetter);
		function.setSecondarySetter(getterSetter);

		function.setPrimaryHandler(handler);
		function.setSecondaryHandler(handler);

		function.setTypeDelegate(handler);

		List<Long> linkIds = new ArrayList<Long>();
		linkIds.add(3L);
		linkIds.add(4L);
		linkIds.add(5L);
		linkIds.add(6L);

		Long objectAIdentifier = 1L;
		LinkerObject objectA = new LinkerObject(objectAIdentifier);
		LinksPair<LinkerObject, Long> pairA = new LinksPair<LinkerObject, Long>(objectA, linkIds, "a", LinksAction.LINK);

		Long objectBIdentifier = 2L;
		LinkerObject objectB = new LinkerObject(objectBIdentifier);
		LinksPair<LinkerObject, Long> pairB = new LinksPair<LinkerObject, Long>(objectB, linkIds, "a", LinksAction.LINK);

		function.addObject(pairA);
		function.addObject(pairB);
		boolean success = function.run();

		Assert.assertTrue(success);
		List<LinkerObject> savedObjects = getterSetter.getSavedObjects();
		Assert.assertTrue(savedObjects.size() == 4);

		LinkerObject object = savedObjects.get(0);
		Set<Long> bSet = object.getSetB();

		Assert.assertTrue(bSet.contains(objectAIdentifier));
		Assert.assertTrue(bSet.contains(objectBIdentifier));
		Assert.assertTrue(objectA.getSetA().contains(object.getId()));
		Assert.assertTrue(objectB.getSetA().contains(object.getId()));
	}

}
