package medico.adrian.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;

import medico.adrian.view.Principal;
import net.xqj.basex.local.BaseXXQDataSource;

public class Server_XQJ implements Pt33Manager {
	Pais p;
	List<String> listaItems = new ArrayList<>();
	
	@Override
	public List<String> getPaises() {
		XQDataSource xqs = settedConnection();
		XQConnection connection = null;
		String aux;
		listaItems.clear();
		try {
			connection = xqs.getConnection();
			XQExpression expr = connection.createExpression();
			
			XQResultSequence resultSequence = expr.executeQuery(PeticionesBD.pedirNombresPaises());
			
			while (resultSequence.next()) {
				aux = resultSequence.getItemAsString(null);
				listaItems.add(aux);
				
			}
			
		} catch (XQException XQe) {
			XQe.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (XQException e) {
				e.printStackTrace();
			}
		}
		
		
		return listaItems;
	}

	@Override
	public void generarInfoPais(String pais) {
		XQDataSource xqs = settedConnection();
		XQConnection connection = null;
		p = new Pais();
		p.setNom(Principal.getComboBoxItem());
		try {
			connection = xqs.getConnection();
			
			XQExpression expr = connection.createExpression();
			
			//first query
			XQResultSequence resultSequence = expr.executeQuery(PeticionesBD.getSumaFronterasPais(pais));
			
			while (resultSequence.next()) {
				float aux = Float.parseFloat(resultSequence.getItemAsString(null));
				long result = Math.round(aux);
				p.setLongitudFronteres(result);
			}
			listaItems.clear();
			//second query
			resultSequence =expr.executeQuery(PeticionesBD.getEtniasDePais(pais));
			String aux;
			while(resultSequence.next()) {
				
				aux = resultSequence.getItemAsString(null);
				listaItems.add("\n" + aux);
			}
		
		} catch (XQException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (XQException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public String generarHTML() {
		XQDataSource xqs = settedConnection();
		String result = null;
		XQConnection connexio = null;
		try{
			Properties serializationProps = new java.util.Properties();
			serializationProps.setProperty("method", "html");
			connexio = xqs.getConnection();
			
			XQExpression expre = connexio.createExpression();
			
			XQResultSequence resposta = expre.executeQuery(PeticionesBD.getHTML());
			
			resposta.writeSequence(new FileOutputStream("htmlPaisosXQJ.html"), serializationProps);
			
			result = resposta.getSequenceAsString(null);
			
			connexio.close();
			
		} catch (XQException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				connexio.close();
			} catch (XQException e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}

	@Override
	public Pais getPais() {
		
		return p;
	}
	
	
	private XQDataSource settedConnection() {
		XQDataSource xqs = new BaseXXQDataSource();
		try {
			xqs.setProperty("serverName", "localhost");
			xqs.setProperty("port", "1984");
			xqs.setProperty("user", "admin");
			xqs.setProperty("password", "admin");
			
		} catch (XQException e) {
			e.printStackTrace();
		}
		return xqs;
	}
}
