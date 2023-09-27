fun bill(priceList: Map<String, Int>, shoppingList: MutableList<String>): Int {
    var total = 0
    for (item in shoppingList) {
        if (item in priceList) {
            total += priceList[item] ?: 0
        }
    }
    return total
}