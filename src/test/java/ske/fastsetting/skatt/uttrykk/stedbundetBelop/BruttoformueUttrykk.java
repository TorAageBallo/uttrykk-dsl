package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

/**
 * Created by jorn ola birkeland on 26.02.15.
 */
public interface BruttoformueUttrykk extends BelopUttrykk {
    public StedbundetBelopUttrykk fritidseiendomHyttekommune();
    public StedbundetBelopUttrykk stedbundenFormue();
    public BelopUttrykk ovrigFormue();
}
