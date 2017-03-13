/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uib.secom.accestur.crypto.rsa;

import java.math.BigInteger;


/**
 *
 * @author oorestisime
 */
public class RSA_PlainText   {
    private BigInteger[] plt;
    public RSA_PlainText(BigInteger[] pt){
        this.plt=pt;
    }
    public BigInteger[] getText(){
        return plt;
    }
}
