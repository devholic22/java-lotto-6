package lotto.service;

import static lotto.constants.LottoRule.LOTTO_NUMBER_LENGTH;
import static lotto.constants.LottoRule.MINIMUM_MATCH_SIZE;

import lotto.model.GoalNumbers;
import lotto.model.Lotto;
import lotto.model.LottoCompany;
import lotto.constants.Prize;
import lotto.model.LottoNumber;
import lotto.model.dto.PrizeResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LottoCompanyService {

    private final LottoCompany lottoCompany;
    private final List<Lotto> lottos;

    private LottoCompanyService(final LottoCompany lottoCompany, final List<Lotto> lottos) {
        this.lottoCompany = lottoCompany;
        this.lottos = lottos;
    }

    public static LottoCompanyService of(final GoalNumbers goalNumbers,
                                         final LottoNumber bonusNumber, final List<Lotto> lottos) {
        LottoCompany lottoCompany = LottoCompany.of(goalNumbers, bonusNumber);
        return new LottoCompanyService(lottoCompany, lottos);
    }

    public List<PrizeResult> evaluateLottos() {
        List<PrizeResult> results = new ArrayList<>();
        results.addAll(evaluateLottosWithBonus());
        results.addAll(evaluateLottosExceptBonus());

        Collections.sort(results);

        return results;
    }

    private List<PrizeResult> evaluateLottosWithBonus() {
        List<PrizeResult> results = new ArrayList<>();
        for (int match = MINIMUM_MATCH_SIZE.getValue(); match <= LOTTO_NUMBER_LENGTH.getValue(); match++) {
            List<Lotto> matchLottos = lottoCompany.collectLottosWithSizeIncludeBonus(lottos, match);
            Prize.findByMatchWithBonus(match)
                    .ifPresent(prize -> savePrizeResult(prize, matchLottos.size(), results));
        }
        return results;
    }

    private List<PrizeResult> evaluateLottosExceptBonus() {
        List<PrizeResult> results = new ArrayList<>();
        for (int match = MINIMUM_MATCH_SIZE.getValue(); match <= LOTTO_NUMBER_LENGTH.getValue(); match++) {
            List<Lotto> matchLottos = lottoCompany.collectLottosWithSizeExceptBonus(lottos, match);
            Prize.findByMatchExceptBonus(match)
                    .ifPresent(prize -> savePrizeResult(prize, matchLottos.size(), results));
        }
        return results;
    }

    private static void savePrizeResult(final Prize prize, final int matchSize, final List<PrizeResult> results) {
        PrizeResult prizeResult = PrizeResult.of(prize.getCondition(), prize.getMoney(), matchSize);
        results.add(prizeResult);
    }
}
