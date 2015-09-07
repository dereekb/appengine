package com.dereekb.gae.server.mail.gae;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.dereekb.gae.server.mail.MailRequest;
import com.dereekb.gae.server.mail.MailSource;
import com.dereekb.gae.server.mail.exceptions.EmailSendFailureException;
import com.dereekb.gae.server.mail.pairs.MailRecipient;
import com.dereekb.gae.server.storage.object.file.StorableContent;

/**
 * Uses the JavaMail API to act as a MailSource.
 * 
 * @author dereekb
 *
 */
public class JavaMailSource
        implements MailSource {

	@Override
	public void sendMail(MailRequest request) throws EmailSendFailureException {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message message = new MimeMessage(session);

		try {
			SendMessageInstance instance = new SendMessageInstance(request);
			instance.prepareMessage(message);

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new EmailSendFailureException();
		}
	}

	private static class SendMessageInstance {

		private final MailRequest request;

		public SendMessageInstance(MailRequest request) {
			this.request = request;
		}

		private void prepareMessage(Message message) throws MessagingException {
			this.appendDetails(message);
			this.appendRecipients(message);
			this.appendAttachments(message);
		}

		private void appendAttachments(Message message) throws MessagingException {

			boolean hasAttachments = this.request.hasAttachments();
			boolean hasHtmlContent = this.request.isHtmlContent();

			if (hasAttachments || hasHtmlContent) {
				Multipart mp = new MimeMultipart();

				if (hasHtmlContent) {
					String content = this.request.getContent();

					MimeBodyPart htmlContent = new MimeBodyPart();
					htmlContent.setContent(content, "text/html");
					mp.addBodyPart(htmlContent);
				}

				if (hasAttachments) {
					for (StorableContent content : this.request.getAttachments()) {
						MimeBodyPart attachment = new MimeBodyPart();

						String filename = content.getFilename();
						String contentType = content.getContentType();
						byte[] bytes = content.getFileData();

						attachment.setFileName(filename);
						attachment.setContent(bytes, contentType);
						mp.addBodyPart(attachment);
					}
				}

				message.setContent(mp);
			}
		}

		private void appendDetails(Message message) throws MessagingException {
			String subject = request.getSubject();
			message.setSubject(subject);

			if (this.request.isHtmlContent() == false) {
				String content = request.getContent();
				message.setText(content);
			}
		}

		private void appendRecipients(Message message) throws MessagingException {

			MailRecipient sender = this.request.getSender();
			InternetAddress senderAddress = this.addressForRecipient(sender);
			message.setFrom(senderAddress);

			for (MailRecipient recipient : this.request.getRecipients()) {
				InternetAddress recipientAddress = this.addressForRecipient(recipient);
				RecipientType type = this.readRecipientType(recipient);

				message.addRecipient(type, recipientAddress);
			}
		}

		private RecipientType readRecipientType(MailRecipient recipient) {

			RecipientType type = RecipientType.TO;
			MailRecipient.RecipientType mailRecipientType = recipient.getRecipientType();

			switch (mailRecipientType) {
				case BCC:
					type = RecipientType.BCC;
					break;
				case CC:
					type = RecipientType.CC;
					break;
				case TO:
					type = RecipientType.TO;
					break;
			}

			return type;
		}

		private InternetAddress addressForRecipient(MailRecipient recipient) {

			String name = recipient.getName();
			String address = recipient.getAddress();

			InternetAddress internetAddress = null;

			try {
				internetAddress = new InternetAddress(address, name);
			} catch (UnsupportedEncodingException e) {

			}

			return internetAddress;
		}
	}
}
