package com.example.demo;
import com.example.demo.ConnectionPostgree;

import org.json.*;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	public List<String> getFoosBySimplePath1(@RequestBody String body) throws JSONException {

	JSONArray jsonarray = new JSONArray(body);


	//jsonarray guarda una petición en cada pos (2 peticiones-> length2,..)
	List<String> consultas = new ArrayList<String>();
	String groupBy="";
	for (int i = 0; i < jsonarray.length(); i++) {

	    JSONObject peticion = jsonarray.getJSONObject(i);


	    int num_col = peticion.getInt("colNum");
	    int num_tab = peticion.getInt("tabNum");
	    int num_cond = peticion.getInt("condNum");
	    if(peticion.has("groupBy")){
	    	groupBy = peticion.getString("groupBy");
	    }


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

	    //CREAR CONSULTA (cada una guardar en un string y añadir a peticiones.add)
	    String select="";
	    for(int s=0; s<num_col;s++) {
	    	if(s==num_col-1) select = select + listCol.get(s);
	    	else select = select + listCol.get(s) + ", ";

	    }

	    String from="";
	    for(int f=0; f<num_tab; f++) {
	    	if(f==num_tab-1) from = from + listTab.get(f);
	    	else select = from = from + listTab.get(f) + ", ";

	    }

	    String where="";
	    for(int w=0; w<num_cond; w++) {
	    	if (w==num_cond-1)
	    		where = where + listCond.get(w);
	    	else
	    		where = where + listCond.get(w) + " AND ";
	    }


	    String consulta="";
	    consulta = "SELECT " + select +  " FROM " + from + " WHERE " + where;
	    if(!groupBy.equals("")) consulta = consulta + " GROUP BY " + groupBy + ";";
	    else consulta = consulta +";";
	    //VER QUE PASA SI NO PONGO GROUP BY? COMPROBAR QUE EXISTA ANTES DE GUARDARLO???

	    consultas.add(consulta);


	}

	List<Integer> tiemposConsulta = new ArrayList<Integer>();
	for(int con =0; con<consultas.size(); con++) {
		System.out.println(consultas.get(con) + "\n");

		/*swith(gestor)
		 * 	if(mariadb) crear obj maradb y mandar
		 * else if(postgre) ...
		 * POR AHORA SOLO CON MARIADB
		 * */
		ConnectionPostgree cmdb = new ConnectionPostgree();
		tiemposConsulta.addAll(cmdb.HacerConsulta(consultas.get(con)));
		//MANDAR TIEMPOS A INTERFAZ


	}
	return consultas;


	}


}

