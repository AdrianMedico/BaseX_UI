package medico.adrian.model;

import java.util.List;
/**
 * 
 * @author Adrián Médico Crespo
 *
 */
public class PeticionesBD {
	
	
	public static final String pedirNombresPaises() {
		String consultaCountry = "" + "for $country in doc('" + ConstantsCommon.NOMBREBD + "')//country"
				+ " order by $country/name"
				+ " return $country/name/data()";
		return consultaCountry;
	}
	
	public static final String getSumaFronterasPais(String pais) {
		if(checkPais(pais)) {
			String consultaTotalFronteras = ""+ "let $result := sum( doc (\"factbook\")//country[name=\""+pais+"\"]/border/@length/data()) return $result";
			
			return consultaTotalFronteras;
		}
		else
			return null;
	}
	
	public static final String getEtniasDePais(String pais){
		if(checkPais(pais)) {
			String consultaEtnias = "" + "let $ethnicgroup := doc (\"factbook\")//country[name=\""+pais+"\"]/ethnicgroups/data()" + 
					" return $ethnicgroup";
			return consultaEtnias;
		}
		return null;
	}
	
	public static final String getHTML(){
		String consulta = "\r\n" + 
				"<html>\r\n" + 
				"  <head>\r\n" + 
				"  <title>Informacion de paises</title>\r\n" + 
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"    <table border=\"1\">\r\n" + 
				"      <tr>\r\n" + 
				"      <th>Nombre</th>\r\n" + 
				"      <th>PIB</th>\r\n" + 
				"      <th>Poblacion</th>\r\n" + 
				"      <th>Num ciudades</th>\r\n" + 
				"      </tr>\r\n" + 
				"      {\r\n" + 
				"        \r\n" + 
				"      for $pais in doc (\"factbook\")/mondial/country\r\n" + 
				"        \r\n" + 
				"        order by $pais\r\n" + 
				"        let $nomPais := $pais/name/text()\r\n" + 
				"        let $pib := $pais/gdp_total/data()\r\n" + 
				"        let $poblacion := $pais/population/data()\r\n" + 
				"        let $suma := count($pais//city)\r\n" + 
				"      \r\n" + 
				"      return\r\n" + 
				"        <tr>\r\n" + 
				"          <td>{$nomPais}</td>\r\n" + 
				"          <td>{$pib}</td>\r\n" + 
				"          <td>{$poblacion}</td>\r\n" + 
				"          <td>{$suma}</td>\r\n" + 
				"        </tr>\r\n" + 
				"      }\r\n" + 
				"    </table>\r\n" + 
				"  </body>\r\n" + 
				"</html>";
		
		return consulta;
	}
	
	private static boolean checkPais(String pais) {
		if(pais.matches("\\W"))
			return false;
		else
			return true;
	}
}
