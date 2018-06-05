import {Component} from '@angular/core';  
import {AlertController} from 'ionic-angular';

@Component({
    templateUrl: 'home.html'
})

export class HomePage {

    public showingWelcome: boolean;
    public productList: Array<Object>;

    public constructor(private alert: AlertController) {
        this.showingWelcome = true;
        this.productList = [];
    }

    public dismissWelcome() {
        this.showingWelcome = false;
    }

    public add() {
        let alert = this.alert.create({
            title: "Add Product",
            message: "Enter a product and the price of that product",
            inputs: [
                {
                    name: "product",
                    placeholder: "Product Name"
                },
                {
                    name: "price",
                    placeholder: "Product Price"
                }
            ],
            buttons: [
                {
                    text: "Cancel"
                },
                {
                    text: "Save",
                    handler: data => {
                        this.productList.push({
                            name: data.product,
                            price: data.price
                        });
                    }
                }
            ]
        });
    alert.present()
    }
}
