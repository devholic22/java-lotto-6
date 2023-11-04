package lotto.model;

import static lotto.model.constants.LottoRule.LOTTO_NUMBER_LENGTH;
import static lotto.view.exception.InputException.NUMBER_DUPLICATE_EXCEPTION;

import java.util.ArrayList;
import java.util.List;

public class Lotto {

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validateIsNumbersUnique(numbers);
        validateNumbersSize(numbers);

        this.numbers = numbers;
    }

    private void validateIsNumbersUnique(final List<Integer> numbers) {
        List<Integer> uniqueNumbers = numbers.stream()
                .distinct()
                .toList();

        if (uniqueNumbers.size() != numbers.size()) {
            throw new IllegalArgumentException(NUMBER_DUPLICATE_EXCEPTION.getMessage());
        }
    }

    private void validateNumbersSize(final List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBER_LENGTH.getValue()) {
            throw new IllegalArgumentException();
        }
    }

    // TODO: 추가 기능 구현
    public boolean isContainsNumber(final int number) {
        return numbers.contains(number);
    }

    public boolean isContainsNumbersWithSize(final List<Integer> goalNumbers, final int size) {
        List<Integer> lottoNumbers = new ArrayList<>(numbers);
        lottoNumbers.retainAll(goalNumbers);

        return lottoNumbers.size() == size;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
