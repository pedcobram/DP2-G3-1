
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompAdminRequests {

	private List<CompAdminRequest> compAdminRequests;


	@XmlElement
	public List<CompAdminRequest> getCompAdminRequestList() {
		if (this.compAdminRequests == null) {
			this.compAdminRequests = new ArrayList<>();
		}
		return this.compAdminRequests;
	}

}
