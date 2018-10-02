package pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.infrastructure;

import pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.domain.IRepository;
import pe.edu.unmsm.fisi.upg.ads.codigolimpiosj.domain.Speaker;

public class SqlServerRepository implements IRepository {
	public int saveSpeaker(Speaker speaker) {
		// TODO: Save speaker to SQL Server DB. For now, just assume success and return 1.
		return 1;
	}

	public int calculateRegistrationFee(int exp) {
		// TODO : Generar consulta en base de datos 
		int registrationFee = 0;
		if (exp <= 1) {
			registrationFee = 500;
		} else if ( exp>= 2 && exp <= 3) {
			registrationFee = 250;
		} else if (exp >= 4 && exp <= 5) {
			registrationFee = 100;
		} else if (exp >= 6 && exp <= 9) {
			registrationFee = 50;
		} else {
			registrationFee = 0;
		}
		return registrationFee;
	}

}