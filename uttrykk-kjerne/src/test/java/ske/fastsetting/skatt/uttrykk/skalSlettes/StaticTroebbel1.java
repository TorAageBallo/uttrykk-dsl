package ske.fastsetting.skatt.uttrykk.skalSlettes;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

/**
 * Created by jorn ola birkeland on 22.03.15.
 */
public class StaticTroebbel1 {

    public static BelopUttrykk ut2 = kr(16);
    public static BelopUttrykk ut4 = StaticTroebbel2.ut3.pluss(kr(19));
}
