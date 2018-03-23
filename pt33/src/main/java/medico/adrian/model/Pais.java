package medico.adrian.model;

import java.util.List;

import cat.iam.ad.uf3.IPais;

public class Pais implements IPais {
	private String nom;
	private long longitudFronteres;
	private List<String> grupsEtnics;
	private String descripcio;
	
	public Pais() {
		
	}
	
	public Pais(String nom, int longitudFronteres) {
		
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;

	}

	public long getLongitudFronteres() {
		
		return longitudFronteres;
	}

	public void setLongitudFronteres(long longitud) {
		this.longitudFronteres = longitud;

	}

	public List<String> getGrupsEtnics() {
		return grupsEtnics;
	}

	public void setGrupsEtnics(List<String> grups) {
		this.grupsEtnics = grups;
	}

	public String creaDescripcio() {
		//NI IDEA DE QUE VA AQUI
		return null;
	}

	@Override
	public String toString() {
		return "Pais [nom=" + nom + ", longitudFronteres=" + longitudFronteres + ", grupsEtnics=" + grupsEtnics + "]";
	}

	
}
