import {Component} from '@angular/core';
import {NavController} from 'ionic-angular';

declare var window: any;

@Component({
  templateUrl: 'home.html'
})
export class HomePage {
  public smsNumber:any;
  public smsText:any;
  constructor(public navCtrl: NavController) {

  }

  sendSMS(){
    if(window.SMS) window.SMS.sendSMS(this.smsNumber, this.smsText,()=>{
       console.log("Message sent");
     },(error)=>{
       console.log("error");
     });
   }

}
