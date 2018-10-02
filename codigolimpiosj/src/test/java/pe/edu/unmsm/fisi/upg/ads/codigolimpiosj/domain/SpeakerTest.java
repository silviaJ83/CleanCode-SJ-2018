package pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.domain;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.domain.Session;
import pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.domain.Speaker;
import pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.domain.WebBrowser;
import pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.exceptions.NoSessionsApprovedException;
import pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.exceptions.SpeakerDoesntMeetRequirementsException;
import pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.infrastructure.SqlServerRepository;

public class SpeakerTest {
	private SqlServerRepository repository = new SqlServerRepository(); // Hard coding to single concrete implementation for simplicity here.

	@Test(expected = IllegalArgumentException.class)
	public void register_EmptyFirstName_ThrowsArgumentNullException() throws Exception {
		Speaker speaker = getSpeakerThatWouldBeApproved();
		speaker.setFirstName("");
		
		speaker.register(repository);
	}

	@Test(expected = IllegalArgumentException.class)
	public void register_EmptyLastName_ThrowsArgumentNullException() throws Exception {
		Speaker speaker = getSpeakerThatWouldBeApproved();
		speaker.setLastName("");
	
		speaker.register(repository);
	}

	@Test(expected = IllegalArgumentException.class)
	public void register_EmptyEmail_ThrowsArgumentNullException() throws Exception {
		Speaker speaker = getSpeakerThatWouldBeApproved();
		speaker.setEmail("");
	
		speaker.register(repository);
	}

	@Test
	public void register_WorksForPrestigiousEmployerButHasRedFlags_ReturnsSpeakerId() throws Exception {
		// arrange
		Speaker speaker = getSpeakerWithRedFlags();
		speaker.setEmployer("Microsoft");

		// act
		Integer speakerId = speaker.register(new SqlServerRepository());

		// assert
		assertNotNull(speakerId);
	}

	@Test
	public void register_HasBlogButHasRedFlags_ReturnsSpeakerId() throws Exception {
		Speaker speaker = getSpeakerWithRedFlags();

		Integer speakerId = speaker.register(new SqlServerRepository());

		assertNotNull(speakerId);
	}

	@Test
	public void register_HasCertificationsButHasRedFlags_ReturnsSpeakerId() throws Exception {
		Speaker speaker = getSpeakerWithRedFlags();
		speaker.setCertifications(Arrays.asList("cert1", "cert2", "cert3", "cert4"));

		Integer speakerId = speaker.register(new SqlServerRepository());

		assertNotNull(speakerId);
	}

	@Test(expected = NoSessionsApprovedException.class)
	public void register_SingleSessionThatsOnOldTech_ThrowsNoSessionsApprovedException() throws Exception {
		Speaker speaker = getSpeakerThatWouldBeApproved();
		speaker.setSessions(Arrays.asList(new Session("Cobol for dummies", "Intro to Cobol")));

		speaker.register(repository);
	}

	@Test(expected = IllegalArgumentException.class)
	public void register_NoSessionsPassed_ThrowsArgumentException() throws Exception {
		Speaker speaker = getSpeakerThatWouldBeApproved();
		speaker.setSessions(new ArrayList<Session>());
	
		speaker.register(repository);
	}

	@Test(expected = SpeakerDoesntMeetRequirementsException.class)
	public void register_DoesntAppearExceptionalAndUsingOldBrowser_ThrowsNoSessionsApprovedException() throws Exception {
		Speaker speakerThatDoesntAppearExceptional = getSpeakerThatWouldBeApproved();
		speakerThatDoesntAppearExceptional.setHasBlog(false);
		speakerThatDoesntAppearExceptional.setBrowser(new WebBrowser("IE", 6));

		speakerThatDoesntAppearExceptional.register(repository);
	}

	@Test(expected = SpeakerDoesntMeetRequirementsException.class)
	public void register_DoesntAppearExceptionalAndHasAncientEmail_ThrowsNoSessionsApprovedException() throws Exception {
		Speaker speakerThatDoesntAppearExceptional = getSpeakerThatWouldBeApproved();
		speakerThatDoesntAppearExceptional.setHasBlog(false);
		speakerThatDoesntAppearExceptional.setEmail("name@aol.com");

		speakerThatDoesntAppearExceptional.register(repository);
	}

	private Speaker getSpeakerThatWouldBeApproved() {
		Speaker speaker = new Speaker();

		speaker.setFirstName("First");
		speaker.setLastName("Last");
		speaker.setEmail("example@domain.com");
		speaker.setEmployer("Example Employer");
		speaker.setHasBlog(true);
		speaker.setBrowser(new WebBrowser("test", 1));
		speaker.setExp(1);
		speaker.setCertifications(new ArrayList<String>());
		speaker.setBlogURL("");
		speaker.setSessions(Arrays.asList(new Session("test title", "test description")));

		return speaker;
	}

	private Speaker getSpeakerWithRedFlags() {
		Speaker speaker = getSpeakerThatWouldBeApproved();
		speaker.setEmail("tom@aol.com");
		speaker.setBrowser(new WebBrowser("IE", 6));
		return speaker;
	}
}