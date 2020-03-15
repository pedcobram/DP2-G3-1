
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Referees {

	private List<Referee> referees;


	@XmlElement
	public List<Referee> getRefereesList() {
		if (this.referees == null) {
			this.referees = new ArrayList<>();
		}
		return this.referees;
	}

}
