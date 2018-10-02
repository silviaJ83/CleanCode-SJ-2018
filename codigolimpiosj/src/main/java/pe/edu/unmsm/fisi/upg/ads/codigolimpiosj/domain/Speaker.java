package pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.domain;

import java.util.Arrays;
import java.util.List;

import pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.exceptions.NoSessionsApprovedException;
import pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.exceptions.SpeakerDoesntMeetRequirementsException;

public class Speaker {
	private static final int MIN_YEAR_EXPERIENCE = 10;
	private static final int MIN_NUMBER_OF_CERTIFICATIONS = 3;
	private static final int MIN_VERSION_IE = 9;
	private String firstName;
	private String lastName;
	private String email;
	private int exp;
	private boolean hasBlog;
	private String blogURL;
	private WebBrowser browser;
	private List<String> certifications;
	private String employer;
	private int registrationFee;
	private List<Session> sessions;

	public Integer register(IRepository repository) throws Exception {
		Integer speakerId = null;
		validateSpeaker();
		calculateRegistrationFee(repository);
		speakerId = repository.saveSpeaker(this);
		return speakerId;
	}

	private void calculateRegistrationFee(IRepository repository) {
		this.registrationFee = repository.calculateRegistrationFee(this.exp);

	}

	private void validateSpeaker() throws SpeakerDoesntMeetRequirementsException, NoSessionsApprovedException {
		if (this.firstName.isEmpty())
			throw new IllegalArgumentException("First Name is required");
		if (this.lastName.isEmpty())
			throw new IllegalArgumentException("Last name is required.");
		if (this.email.isEmpty())
			throw new IllegalArgumentException("Email is required.");

		if (hasRedFlags() && !hasValidEmailAndBrowser())
			throw new SpeakerDoesntMeetRequirementsException(
					"Speaker doesn't meet our abitrary and capricious standards.");

		if (this.sessions.size() == 0)
			throw new IllegalArgumentException("Can't register speaker with no sessions to present.");

		if (!hasApprovedSessions())
			throw new NoSessionsApprovedException("No sessions approved.");
	}

	private boolean hasApprovedSessions() {
		boolean appr = false;
		String[] oldTechnologies = new String[] { "Cobol", "Punch Cards", "Commodore", "VBScript" };

		for (Session session : sessions) {
			for (String tech : oldTechnologies) {
				if (session.getTitle().contains(tech) || session.getDescription().contains(tech)) {
					session.setApproved(false);
					break;
				} else {
					session.setApproved(true);
					appr = true;
				}
			}
		}
		return appr;
	}

	private boolean hasValidEmailAndBrowser() {
		boolean validation = false;
		List<String> ancientEmailDomains = Arrays.asList("aol.com", "hotmail.com", "prodigy.com", "compuserve.com");

		String[] splitted = this.email.split("@");
		String emailDomain = splitted[splitted.length - 1];

		if (!ancientEmailDomains.contains(emailDomain) && (!(browser.getName() == WebBrowser.BrowserName.InternetExplorer && browser.getMajorVersion() < MIN_VERSION_IE))) {
			validation = true;
		}
		return validation;
	}

	private boolean hasRedFlags() {
		List<String> prestigiousEmployers = Arrays.asList("Pluralsight", "Microsoft", "Google", "Fog Creek Software",
				"37Signals", "Telerik");
		return !(this.exp > MIN_YEAR_EXPERIENCE || this.hasBlog || this.certifications.size() > MIN_NUMBER_OF_CERTIFICATIONS
				|| prestigiousEmployers.contains(this.employer));
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public boolean isHasBlog() {
		return hasBlog;
	}

	public void setHasBlog(boolean hasBlog) {
		this.hasBlog = hasBlog;
	}

	public String getBlogURL() {
		return blogURL;
	}

	public void setBlogURL(String blogURL) {
		this.blogURL = blogURL;
	}

	public WebBrowser getBrowser() {
		return browser;
	}

	public void setBrowser(WebBrowser browser) {
		this.browser = browser;
	}

	public List<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public int getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(int registrationFee) {
		this.registrationFee = registrationFee;
	}


}
