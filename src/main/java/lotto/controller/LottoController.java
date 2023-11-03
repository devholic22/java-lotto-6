package lotto.controller;

import lotto.model.Money;
import lotto.view.input.InputView;
import lotto.view.output.OutputView;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.askInvestMoney();
        String moneyInput = inputView.readLine();
        Money investMoney = Money.from(moneyInput);
    }
}
