package com.example.demo;
import com.example.demo.ConnectionPostgree;

import org.json.*;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PetitionController {

	@RequestMapping(value="/api/hi", method = RequestMethod.GET)
	@ResponseBody
	public String getFoosBySimplePath() {
	    return "Get some Foos";
	}

	@RequestMapping(value="/api/hi", method = RequestMethod.POST)
	@ResponseBody
	public List<?> getFoosBySimplePath1(@RequestBody String body) throws JSONException {

	JSONArray jsonarray = new JSONArray(body);


	//jsonarray guarda una petición en cada pos (2 peticiones-> length2,..)
	Map <String, String> consultaGestor = new HashMap<String, String>();
	String groupBy="";
	for (int i = 0; i < jsonarray.length(); i++) {

	    JSONObject peticion = jsonarray.getJSONObject(i);


	    int num_col = peticion.getInt("colNum");
	    int num_tab = peticion.getInt("tabNum");
	    int num_cond = peticion.getInt("condNum");
	    if(peticion.has("groupBy")) groupBy = peticion.getString("groupBy");
	    String gestor = peticion.getString("bd");
	    


	    List<String> listCol = new ArrayList<String>();
	    for (int col=1; col<=num_col; col++) {
	    	String columna = peticion.getString("nom" + col);
	    	listCol.add(columna);
	    }

	    List<String> listTab = new ArrayList<String>();
	    for (int tab=1; tab<=num_tab; tab++) {
	    	String tabla = peticion.getString("tab" + tab);
	    	listTab.add(tabla);
	    }

	    List<String> listCond = new ArrayList<String>();
	    for (int cond=1; cond<=num_cond; cond++) {
	    	String condicion = peticion.getString("cond" + cond);
	    	listCond.add(condicion);
	    }

	    System.out.println("JSONOBJECT " + i + ":" + peticion + "\n" );
	    System.out.println("josnobj " + i + " NUM TABLAS  :" + num_tab + " NUM COL: " + num_col + " NUM COND: " + num_cond + "\n");

	    System.out.println("columnas: " + listCol + "\n");
	    System.out.println("tablas: " + listTab + "\n");
	    System.out.println("condiciones: " + listCond + "\n");

	    //CREAR CONSULTA 

	    //Lista columnas
	    String select="";
	    for(int s=0; s<num_col;s++) {
	    	if(s==num_col-1) select = select + listCol.get(s);
	    	else select = select + listCol.get(s) + ", ";
	    }

	    //Lista tablas
	    String from="";
	    for(int f=0; f<num_tab; f++) {
	    	if(f==num_tab-1) from = from + listTab.get(f);
	    	else from = from + listTab.get(f) + ", ";
	    }
	    
	    //Lista condiciones
	    String where="";
	    for(int w=0; w<num_cond; w++) {
	    	if (w==num_cond-1)
	    		where = where + listCond.get(w);
	    	else
	    		where = where + listCond.get(w) + " AND ";
	    }

	    //Crear consulta
	    String consulta="";
	    
	    //Consulta Mongo db (no relacional)
		if(gestor.equals("mongodb")) {
			consulta = "db." + from + ".find({" +  where +"});";
			consultaGestor.put(consulta, gestor);
		}
		//Consulta xml

		else if(gestor.equals("XML")) {
			String consulta4 = "for $x in doc(\"/db/pruebecita.xml\")//\n" +
					"     table_data/row/field[@name=\"transcript_id\"]\n" +
					"     where data($x)=\"862540\"\n" +
					"     return $x/.";
			String consulta3 = "for $x in doc(\"/db/pruebecita.xml\")//\n" +
					"table_data/row/field[@name=\"gene_id\"]\n" +
					"return $x/../field[@name=\"biotype\"]";
			String consulta2 = "for $x in doc(\"/db/pruebecita.xml\")//\n" +
					"table_data/row/field[@name=\"transcript_id\"]\n" +
					"return $x/..";
			String consulta1 = "for $x in doc(\"/db/pruebecita.xml\")//table_data/row/field[@name=\"transcript_id\"]\n where data($x)=\"862540\"" +
			"return $x/../field[@name=\"gene_id\"]";
			consulta = "for $x in doc(\"/db/pruebecita.xml\")//table_data/row/field[@name=\"gene_id\"]\n" +
					"return $x/../field[@name=\"biotype\"]";
			consultaGestor.put(consulta4, gestor);
		}
	    //Consulta relacional
	    else {
	    	consulta = "SELECT " + select +  " FROM " + from + " WHERE " + where;
		    if(!groupBy.equals("")) consulta = consulta + " GROUP BY " + groupBy + ";";
		    else consulta = consulta +";";
		    consultaGestor.put(consulta, gestor);
	    }
	    
	}
	
		

	List<Object> tiemposConsulta = new ArrayList<Object>();
	
	for(Entry<String, String> entry: consultaGestor.entrySet()) {

		System.out.println("KEY:"+ entry.getKey() + "\n");
        System.out.println("VALUE" + entry.getValue() + "\n");

		if(entry.getValue().equals("mongodb")) {
            ConnectionNoSQL cnn = new ConnectionNoSQL();
            tiemposConsulta.addAll(cnn.HacerConsulta(entry.getKey()));
            System.out.println("Case nosql: " + entry.getValue() + "\n");
        }
        else if (entry.getValue().equals("mariadb")) {
            ConnectionMariaDB cnn_mdb = new ConnectionMariaDB();
			List<Float> cnn = new ArrayList<>();
			cnn.addAll(cnn_mdb.HacerConsulta(entry.getKey()));
			String petit = "\nBDB_CICA: "  + cnn.get(0)+  "\nBDB_CISA: "+ cnn.get(1) + "\nBDB_SICA: "+cnn.get(2)
						+ "\nBDB_SISA: "+ cnn.get(3);
			tiemposConsulta.add(petit);
			System.out.println("Case mariadb" + entry.getValue()+"\n"+ petit + "\n");

        }
        else if (entry.getValue().equals("psql")){
		    ConnectionPostgree cnn_ps = new ConnectionPostgree();
			List<Float> cnn = new ArrayList<>();
			cnn.addAll(cnn_ps.HacerConsulta(entry.getKey()));
			String petit = "\nBDB_CICA: "  + cnn.get(0)+  "\nBDB_CISA: "+ cnn.get(1) + "\nBDB_SICA: "+cnn.get(2)
					+ "\nBDB_SISA: "+ cnn.get(3);
			tiemposConsulta.add(petit);
		    System.out.println("case psql : "+ entry.getValue()+ petit + "\n");
		}
		else if (entry.getValue().equals("mysql")){
		    ConnectionMysql cnn_mys = new ConnectionMysql();
			List<Float> cnn = new ArrayList<>();
		    cnn.addAll(cnn_mys.HacerConsulta(entry.getKey()));
			String petit = "\n BDB_CICA: "  + cnn.get(0)+  "\n BDB_CISA: "+ cnn.get(1) + "\n BDB_SICA: "+cnn.get(2)
					+ "\n BDB_SISA: "+ cnn.get(3);
			tiemposConsulta.add(petit);
		    System.out.println("case mysql: " + entry.getValue()+ petit + "\n");
		}
		else if (entry.getValue().equals("XML")){
			ConnectionXML cnn_xml = new ConnectionXML();
			tiemposConsulta.addAll(cnn_xml.HacerConsulta(entry.getKey()));
			System.out.println("case XML: " + entry.getValue() + "\n");
		}


		else{
		    System.out.println("no ha funsionao");
            System.out.println(entry.getValue());
		}
		
		

	}
	
	
	
	return tiemposConsulta;


	}


}

