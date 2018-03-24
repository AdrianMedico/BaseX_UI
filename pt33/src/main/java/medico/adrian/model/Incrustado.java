package medico.adrian.model;

import java.util.ArrayList;
import java.util.List;

import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;
import org.basex.query.QueryException;
import org.basex.query.QueryProcessor;
import org.basex.query.func.db.DbOpen;
import org.basex.query.iter.Iter;
import org.basex.query.value.item.Item;

import cat.iam.ad.uf3.IPais;
import medico.adrian.view.Principal;

/**
 * @author Adrián Médico Crespo 
 */
public class Incrustado implements Pt33Manager {
	private Context context = new Context();
	private List<String> listaItems = new ArrayList<String>();
	private Pais p;

	public List<String> getNombresPaises() {
		return listaItems;
	}

	public List<String> getPaises() {

		String it;
		// String respuesta = "";

		context = openContext();

		QueryProcessor processor = new QueryProcessor(PeticionesBD.pedirNombresPaises(), context);

		try {
			Iter iter = processor.iter();
			for (Item item; (item = iter.next()) != null;) {
				it = item.toJava().toString();
				listaItems.add(it);
			}
		} catch (QueryException e1) {
			e1.printStackTrace();
		}

		context.close();

		return listaItems;
	}

	public void generarInfoPais(String pais) {

		context = openContext();

		QueryProcessor processor = new QueryProcessor(PeticionesBD.getSumaFronterasPais(pais), context);

		// PAIS
		p = new Pais();

		p.setNom(Principal.getComboBoxItem());

		p.toString();

		try {
			Iter iter = processor.iter();
			for (Item item; (item = iter.next()) != null;) {
				float aux = Float.parseFloat(item.toJava().toString());
				long result = Math.round(aux);
				// System.out.println("FRONTERAS!!!: " + result);
				p.setLongitudFronteres(result);
			}
			QueryProcessor processor2 = new QueryProcessor(PeticionesBD.getEtniasDePais(pais), context);
			String it;
			listaItems.clear();
			iter = processor2.iter();
			for (Item item; (item = iter.next()) != null;) {
				it = item.toJava().toString();
//				System.out.println(it);
				listaItems.add("\n" + it);
			}

		} catch (QueryException exQueryException) {
			exQueryException.printStackTrace();
		} finally {
			context.close();
		}

		p.setGrupsEtnics(listaItems);

	}

	public String generarHTML() {
		context = openContext();

//		QueryProcessor processor = new QueryProcessor(PeticionesBD.getHTML(), context);
		String resposta = null;
		try {
			resposta = new XQuery(PeticionesBD.getHTML()).execute(context);
		} catch (BaseXException e) {
			resposta = e.getMessage();
			e.printStackTrace();
		}
		context.close();
		return resposta + "\nIncrustado";
	}

	public Pais getPais() {

		return p;
	}

	private Context openContext() {
		try {
			new Open(ConstantsCommon.NOMBREBD, "src/main/" + ConstantsCommon.NOMBREBD + ".xml").execute(context);

		} catch (BaseXException e) {
			System.out.println("La base de dades no existe, creando...");
			try {
				new CreateDB(ConstantsCommon.NOMBREBD, "src/main/" + ConstantsCommon.NOMBREBD + ".xml")
						.execute(context);
			} catch (BaseXException e1) {
				e1.printStackTrace();
			}
		}
		return context;
	}

}
