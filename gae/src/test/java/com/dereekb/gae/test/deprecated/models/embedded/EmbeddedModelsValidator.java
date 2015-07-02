package com.thevisitcompany.gae.test.deprecated.models.embedded;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.BindingResult;

import com.thevisitcompany.gae.deprecated.model.general.Contacts;
import com.thevisitcompany.gae.deprecated.model.general.Media;
import com.thevisitcompany.gae.deprecated.model.general.Timespan;
import com.thevisitcompany.gae.deprecated.model.general.Website;
import com.thevisitcompany.gae.model.general.geo.Point;
import com.thevisitcompany.gae.test.model.extension.validation.ModelValidationTest;

public class EmbeddedModelsValidator extends ModelValidationTest {

	@SuppressWarnings("unused")
	private class TestContainer<T> {

		@Valid
		private List<T> list;

		public TestContainer(T object) {
			this.list = new ArrayList<T>();
			this.list.add(object);
		}

		public TestContainer(List<T> list) {
			this.list = list;
		}

		public List<T> getList() {
			return list;
		}

		public void setList(List<T> list) {
			this.list = list;
		}

	}

	@Test
	public void testLocations() {
		Point location = new Point();
		TestContainer<Point> container = new TestContainer<Point>(location);

		Assert.assertTrue(this.isValid(location));
		Assert.assertTrue(this.isValid(container));

		location.setName("A Test Location");
		location.setTag("A Test Address");
		location.setLatitude(0);
		location.setLongitude(0);
		location.setZoom(10);

		BindingResult result = this.validate(location);
		Assert.assertFalse(result.hasErrors());
		Assert.assertTrue(this.isValid(container));

		location.setLatitude(91);
		Assert.assertTrue(this.isInvalid(location));
		Assert.assertTrue(this.isInvalid(container));

		location.setLatitude(-91);
		Assert.assertTrue(this.isInvalid(location));
		Assert.assertTrue(this.isInvalid(container));

		location.setLatitude(-90);
		Assert.assertTrue(this.isValid(location));
		Assert.assertTrue(this.isValid(container));

		location.setLatitude(90);
		Assert.assertTrue(this.isValid(location));
		Assert.assertTrue(this.isValid(container));

		location.setZoom(25);
		Assert.assertTrue(this.isInvalid(location));
		Assert.assertTrue(this.isInvalid(container));

		location.setZoom(-1);
		Assert.assertTrue(this.isInvalid(location));
		Assert.assertTrue(this.isInvalid(container));

		location.setZoom(5);
		Assert.assertTrue(this.isValid(location));
		Assert.assertTrue(this.isValid(container));
	}

	@Test
	public void testMedia() {
		Media media = new Media();
		TestContainer<Media> container = new TestContainer<Media>(media);

		// Empty is Valid
		Assert.assertTrue(this.isValid(media));
		Assert.assertTrue(this.isValid(container));

		media.setFacebook("theVisitApp");
		media.setTwitter("theVisitApp");

		Assert.assertTrue(this.isValid(media));
		Assert.assertTrue(this.isValid(container));

		media.setFacebook("Facebook_Has_A_Limit_Of_Fifty_Or_So_Characters_So_This_Is_Long");
		Assert.assertTrue(this.isInvalid(media));
		Assert.assertTrue(this.isInvalid(container));

		media.setFacebook("A_Very_Very_Very_Long_URI_But_Is_Valid");
		Assert.assertTrue(this.isValid(media));
		Assert.assertTrue(this.isValid(container));

		media.setTwitter("No_@_Is_Required_But_Less_Than_20_Is");
		Assert.assertTrue(this.isInvalid(media));
		Assert.assertTrue(this.isInvalid(container));

		media.setTwitter("Longest_Handle");
		Assert.assertTrue(this.isValid(media));
		Assert.assertTrue(this.isValid(container));
	}

	@Test
	public void testContacts() {
		Contacts contacts = new Contacts();
		TestContainer<Contacts> container = new TestContainer<Contacts>(contacts);

		Assert.assertTrue(this.isValid(contacts));
		Assert.assertTrue(this.isValid(container));

		contacts.setEmail("test@theVisitApp.com");
		contacts.setPhone("+12101234567");

		Assert.assertTrue(this.isValid(contacts));
		Assert.assertTrue(this.isValid(container));

		contacts.setEmail("aBadEmail@");
		Assert.assertTrue(this.isInvalid(contacts));
		Assert.assertTrue(this.isInvalid(container));

		contacts.setEmail("aGoodEmail@visit.app");
		Assert.assertTrue(this.isValid(contacts));

		contacts.setPhone("Bad");
		Assert.assertTrue(this.isInvalid(contacts));

		contacts.setPhone("1231234567");
		Assert.assertTrue(this.isInvalid(contacts));

		contacts.setPhone("123");
		Assert.assertTrue(this.isInvalid(contacts));

		contacts.setPhone("+12101234567");
		Assert.assertTrue(this.isValid(contacts));
	}

	@Test
	public void testWebsites() {
		Website website = new Website();
		TestContainer<Website> container = new TestContainer<Website>(website);

		Assert.assertTrue(this.isValid(website));
		Assert.assertTrue(this.isValid(container));

		website.setTitle("Website Title");
		website.setUrl("http://www.theVisitApp.com");

		Assert.assertTrue(this.isValid(website));
		Assert.assertTrue(this.isValid(container));

		website.setUrl("theVisitApp.com");

		Assert.assertTrue(this.isInvalid(website));
		Assert.assertTrue(this.isInvalid(container));

		website.setUrl("www.theVisitApp.com");

		Assert.assertTrue(this.isInvalid(website));
		Assert.assertTrue(this.isInvalid(container));

		website.setTitle("A Very Very Very Long Website Title");
		website.setUrl("http://www.theVisitApp.com");

		Assert.assertTrue(this.isInvalid(website));
		Assert.assertTrue(this.isInvalid(container));
	}

	@Test
	public void testTimespans() {
		Timespan timespan = new Timespan();
		TestContainer<Timespan> container = new TestContainer<Timespan>(timespan);

		timespan.setDaysByte(1);

		Assert.assertTrue(this.isValid(timespan));
		Assert.assertTrue(this.isValid(container));

		timespan.setFrom(0);
		timespan.setTo(1440);

		Assert.assertTrue(this.isValid(timespan));
		Assert.assertTrue(this.isValid(container));

		// Days Must be Set/Greater than 0
		timespan.setDaysByte(0);

		Assert.assertTrue(this.isInvalid(timespan));
		Assert.assertTrue(this.isInvalid(container));

		timespan.setDaysByte(255);

		Assert.assertTrue(this.isValid(timespan));
		Assert.assertTrue(this.isValid(container));

		timespan.setFrom(-1);

		Assert.assertTrue(this.isInvalid(timespan));
		Assert.assertTrue(this.isInvalid(container));

		// TODO: Add other new embedded models?
	}

}
