package lotto.service;

import static lotto.model.LottoConstant.FIFTH_PRIZE_MATCH;
import static lotto.model.LottoConstant.FIRST_PRIZE_MATCH;
import static lotto.model.LottoConstant.GOAL_MATCH_POINT;
import static lotto.model.LottoConstant.SECOND_PRIZE_MATCH;

import lotto.model.Lotto;
import lotto.model.LottoCompany;
import lotto.model.Prize;
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

    public static LottoCompanyService of(final LottoCompany lottoCompany, final List<Lotto> lottos) {
        return new LottoCompanyService(lottoCompany, lottos);
    }

    public List<PrizeResult> evaluateLottos() {
        List<PrizeResult> results = new ArrayList<>();
        for (int i = FIFTH_PRIZE_MATCH.getValue(); i <= FIRST_PRIZE_MATCH.getValue(); i++) {
            List<Lotto> matchLottos = lottoCompany.calculateLottosWithSizeExceptBonus(lottos, i);
            Prize prize = Prize.findByPoint(i * GOAL_MATCH_POINT.getValue());
            PrizeResult prizeResult = PrizeResult.of(prize.getCondition(), prize.getMoney(), matchLottos.size());
            results.add(prizeResult);
        }
        List<Lotto> secondPrizeLottos = lottoCompany.calculateLottosWithSizeIncludeBonus(lottos, SECOND_PRIZE_MATCH.getValue());
        Prize secondPrize = Prize.SECOND;
        PrizeResult secondPrizeResult = PrizeResult.of(secondPrize.getCondition(), secondPrize.getMoney(), secondPrizeLottos.size());

        results.add(secondPrizeResult);

        Collections.sort(results);
        return results;
    }
}
