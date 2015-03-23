package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopDiffUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopDividertMedBelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.Arrays;

public interface StedbundetBelopUttrykk<K> extends Uttrykk<StedbundetBelop<K>> {
    default StedbundetBelopSumUttrykk<K> pluss(StedbundetBelopUttrykk<K> ledd) { return StedbundetBelopSumUttrykk.sum(Arrays.asList(this, ledd));}

    default StedbundetBelopDivisjonsUttrykk<K> dividertMed(TallUttrykk tall) {return new StedbundetBelopDivisjonsUttrykk<>(this,tall);}
    default BelopDividertMedBelopUttrykk dividertMed(StedbundetBelopUttrykk<K> divident) {return new BelopDividertMedBelopUttrykk(this.steduavhengig(), divident.steduavhengig());}
    default BelopDividertMedBelopUttrykk dividertMed(BelopUttrykk divident) {return new BelopDividertMedBelopUttrykk(this.steduavhengig(), divident);}

    default StedbundetBelopMultiplikasjonsUttrykk<K> multiplisertMed(Uttrykk<Tall> tall) {return new StedbundetBelopMultiplikasjonsUttrykk<>(this,tall);}

    default StedbundetBelopDiffUttrykk<K> minus(StedbundetBelopUttrykk<K> ledd) { return new StedbundetBelopDiffUttrykk<K>(this,ledd);}

    default BelopDiffUttrykk minus(BelopUttrykk ledd) { return new TilSteduavhengigBelopUttrykk(this).minus(ledd);}
    default BelopSumUttrykk pluss(BelopUttrykk ledd) { return new TilSteduavhengigBelopUttrykk(this).pluss(ledd);}

    default TilSteduavhengigBelopUttrykk steduavhengig() {
        return new TilSteduavhengigBelopUttrykk(this);
    }

    default StedbundetBelopForholdsmessigFordelingDiffUttrykk<K> minusProporsjonalt(BelopUttrykk ledd) { return new StedbundetBelopForholdsmessigFordelingDiffUttrykk<>(this, ledd);}
    default StedbundetBelopForholdsmessigFordelingSumUttrykk<K> plussProporsjonalt(BelopUttrykk belop) { return new StedbundetBelopForholdsmessigFordelingSumUttrykk<>(this, belop);}

    default StedbundetBelopMinusStedUttrykk<K> minusSted(StedbundetBelopUttrykk<K> ledd) { return new StedbundetBelopMinusStedUttrykk<>(this,ledd);}


}
