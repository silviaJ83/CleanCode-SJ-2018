package pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.domain;


public interface IRepository {
	int saveSpeaker(Speaker speaker);
	
	int calculateRegistrationFee(int exp)  ;
}