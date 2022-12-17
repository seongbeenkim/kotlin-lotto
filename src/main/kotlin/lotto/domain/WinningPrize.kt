package lotto.domain

import lotto.domain.vo.PurchaseAmount

enum class WinningPrize(val matchedCount: Int, val prize: Int, val hasBonusNumber: Boolean) {
    FIRST_PRIZE(6, 2_000_000_000, false),
    SECOND_PRIZE(5, 30_000_000, true),
    THIRD_PRIZE(5, 1_500_000, false),
    FOURTH_PRIZE(4, 50_000, false),
    FIFTH_PRIZE(3, 5_000, false),
    NONE(0, 0, false);

    fun calculateRateOfReturn(purchaseAmount: PurchaseAmount): Double {
        return prize.toDouble() / purchaseAmount.amount
    }

    companion object {
        fun find(matchedCount: Int, hasBonusNumber: Boolean = false): WinningPrize {
            val winningPrize = values().firstOrNull { it.matchedCount == matchedCount } ?: return NONE

            return winningPrize.takeIf { it == SECOND_PRIZE && it.hasBonusNumber != hasBonusNumber }?.let { THIRD_PRIZE } ?: winningPrize
        }
    }
}
