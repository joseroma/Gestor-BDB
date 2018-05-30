import { Component} from '@angular/core';
import { NgForm } from '@angular/forms';
import{HttpClient, HttpErrorResponse} from '@angular/common/http';

interface PetitionResponse {
  login:string,
  bio:string,
  company:string
}


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})

export class AppComponent {


  title:string = 'Gestor de peticiones';
  msg:string = '';
  hideUpdate:boolean = true;
  mostrarResul:boolean=true;
  petition =[];
  tiempos = [1.2,1.3,0.8,0.5,2.1,1.3,1.4,0.7,0.4,0.6];
	model2:any={};
	ocultaGroupBy:boolean=true;

  constructor(private _http: HttpClient) {

  }

  enviarPost():void{
		const req= this._http.post("http://localhost:8080/api/hi/", this.petition,{responseType: 'text'} )
		.subscribe(
			res=> {
				console.log("Peticion POST realizada al servidor");
				console.log(res);
				console.log("Esperando respuesta...");
			},
			err=>{
				console.log("Peticion fallida");
				console.log(err);
			}
		)
	}



	savePetition(petitionForm: NgForm):void{
		this.petition.push(petitionForm.value);
   		this.msg='Petición añadida exitosamente!';
    	this.ocultaGroupBy=true;
	}

	resultados():void{
		this.mostrarResul=false;
	}

	limpiar():void{
		this.mostrarResul=true;
		this.petition=[];
	}

	apareceGroup():void{
		this.ocultaGroupBy=false;

	}
	myFunction() {
		location.reload();
	}
	
	updatePetition(petitionForm: NgForm):void{

		let i =this.myValue;
		for(let j =0 ;j< this.petition.length;j++){
			if(i == j){
				this.petition[i]=petitionForm.value;

			}
		}
		this.msg='Peticion actualizada';
		this.hideUpdate=true;
	}





	deletePetition(i):void{
		var answer = confirm('¿Seguro que quiere eliminar la peticion?');
		console.log(answer);
		if(answer){
			this.petition.splice(i, 1);
		}
		this.msg='Peticion eliminada';
	}

	myValue;
	editPetition(i):void{
		this.hideUpdate=false;
		this.model2.fullName=this.petition[i].fullName;
		this.model2.DB=this.petition[i].DB;
		this.model2.search=this.petition[i].search;
		this.model2.extendQuery=this.petition[i].extendQuery;
		this.model2.extendSearch=this.petition[i].extendSearch;
		this.myValue = i;
	}



	closeAlert():void{
		this.msg='';
	}



	openCity(evt, cityName) {
		var i, tabcontent, tablinks;
		tabcontent = document.getElementsByClassName("tabcontent");
		for (i = 0; i < tabcontent.length; i++) {
			tabcontent[i].style.display = "none";
		}
		tablinks = document.getElementsByClassName("tablinks");
		for (i = 0; i < tablinks.length; i++) {
			tablinks[i].className = tablinks[i].className.replace(" active", "");
		}
		document.getElementById(cityName).style.display = "block";
		evt.currentTarget.className += " active";
	}




}
