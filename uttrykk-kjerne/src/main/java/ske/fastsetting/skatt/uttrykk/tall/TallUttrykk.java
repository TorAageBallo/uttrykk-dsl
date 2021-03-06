package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Avrunding;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;

public interface TallUttrykk extends CompareableUttrykk<Tall> {
    default TallUttrykk multiplisertMed(TallUttrykk verdi) {
        return new TallMultiplikasjonsUttrykk(this, verdi);
    }

    default TallUttrykk dividertMed(TallUttrykk verdi) {
        return new TallDivisjonsUttrykk(this, verdi);
    }

    default TallUttrykk pluss(TallUttrykk verdi) {
        return TallSumUttrykk.sum(this, verdi);
    }

    @Deprecated()
    default TallAvrundUttrykk rundOpp() {
        return new TallAvrundUttrykk(this, 2, Avrunding.Opp);
    }

    default TallAvrundUttrykk rundAv(int presisjon, Avrunding avrunding) {
        return new TallAvrundUttrykk(this, presisjon, avrunding);
    }

}
