package medico.adrian.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.basex.api.client.ClientSession;
import medico.adrian.view.Principal;
import org.basex.api.client.ClientQuery;

public class BaseX_API implements Pt33Manager {
	Pais p;
	
	List<String> listaItems = new ArrayList<>();

	public ClientSession getOpenSession() {
		ClientSession session = null; 
		try {
			session = new ClientSession("localhost", 1984, "admin", "admin");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return session;
	}
	
	
	public List<String> getPaises() {
		listaItems.clear();
		ClientSession session = getOpenSession();
		ClientQuery query;
		try {
			query = session.query(PeticionesBD.pedirNombresPaises());
			String aux;
			while(query.more()) {
				aux = query.next();
				listaItems.add("\n" + aux);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listaItems;
	}

	public void generarInfoPais(String pais) {
		ClientSession session;
		try {
			//url / Port / user / pwd
			session = getOpenSession();
			ClientQuery query = session.query(PeticionesBD.getSumaFronterasPais(pais));
			ClientQuery query2 = session.query(PeticionesBD.getEtniasDePais(pais));
			p = new Pais();
			
			p.setNom(Principal.getComboBoxItem());
			
			p.toString();
			
			while (query.more()) {
				float aux = Float.parseFloat(query.next());
				long result = Math.round(aux);
				p.setLongitudFronteres(result);
			}
			
			listaItems.clear();
			String aux;
			while(query2.more()) {
				aux = query2.next();
				listaItems.add("\n" + aux);
			}
			session.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		p.setGrupsEtnics(listaItems);
	}

	public String generarHTML() {
		ClientSession session = getOpenSession();
		String result = null;
		ClientQuery query;
		try {
			query = session.query(PeticionesBD.getHTML());
			result = query.execute();
			File f = new File("htmlPaisosBaseX_API.html");
			if(f.isFile()) {
				PrintWriter pw = new PrintWriter(f);
				pw.write(result);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	

	public Pais getPais() {
		
		return p;
	}
}
