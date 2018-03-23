package medico.adrian.model;

import java.util.List;

public interface Pt33Manager {
	/**
	 * 
	 * @return una list con los nombres de todos los paises de la base de datos
	 */
	public List<String> getPaises();
	
	/**
	 * 
	 * @param pais El nombre del pais, no acepta simbolos.
	 */
	public void generarInfoPais(String pais);
	
	/**
	 * 
	 * @return Devuelve un String con una tabla html formatada, con todos los paises 
	 */
	public String generarHTML();
	
	/**
	 * 
	 * @return devuelve la instancia del pais actual
	 */
	public Pais getPais();
}
