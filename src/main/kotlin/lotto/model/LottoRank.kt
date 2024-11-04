package lotto.model

enum class LottoRank(
    val message: String,
    val price: Int,
    val matchingNumbers: Int,
    val matchesBonusNumber: Boolean,
) {
    NONE_RANK("꽝",0,0,false),
    FIFTH_RANK("3개 일치",5_000,3,false),
    FOURTH_RANK("4개 일치",50_000,4,false),
    THIRD_RANK("5개 일치",1_500_000,5,false),
    SECOND_RANK("5개 일치, 보너스 볼 일치", 30_000_000,5,true),
    FIRST_RANK("6개 일치", 2_000_000_000,6,false);

    companion object {
        fun matchingLottoNumber(winningNumbers: Lotto, bonusNumber: Int, purchaseLotto: Lotto): Pair<Int, Boolean> {
            val winningLotto = winningNumbers.getLotto()
            val userLotto = purchaseLotto.getLotto()

            var matchingCount = 0
            val isBonusNumber = containsBonusNumber(bonusNumber, purchaseLotto)

            userLotto.forEach { lottoNumber ->
                if (winningLotto.contains(lottoNumber)) {
                    matchingCount++
                }
            }

            return Pair(matchingCount, isBonusNumber)
        }

        private fun containsBonusNumber(bonusNumber: Int, purchaseLotto: Lotto) = purchaseLotto.getLotto().contains(bonusNumber)

        fun matchingLottoRank(matchingResult: Pair<Int, Boolean>): LottoRank {
            return when (matchingResult) {
                Pair(6, false) -> FIRST_RANK
                Pair(5, true) -> SECOND_RANK
                Pair(5, false) -> THIRD_RANK
                Pair(4, false) -> FOURTH_RANK
                Pair(3, false) -> FIFTH_RANK
                else -> NONE_RANK
            }
        }
    }

}