package edu.cuny.qc.cs348.chatBox;

import java.util.UUID;

public class Message {
	private String id;
	private String from;
	private String message;
	private String attachment;
	private String attachmentName;

	public Message(String from, String message, String attachment, String attachmentName) {
		this.id = UUID.randomUUID().toString();
		this.from = from;
		this.message = message;
		this.attachment = attachment;
		this.setAttachmentName(attachmentName);
	}

	public Message() {
		this.id = UUID.randomUUID().toString();
		this.from = null;
		this.message = null;
		this.attachment = null;
	}

	public String getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (this.attachment == null) {
			return "> " + from + ": " + message;
		}
		return "> " + from + " sent " + this.attachmentName + ", CLICK to open!";

	}
}
