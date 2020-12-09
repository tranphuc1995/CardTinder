package com.tranphuc.home_page.mapper

import com.tranphuc.domain.model.Person
import com.tranphuc.home_page.R
import com.tranphuc.home_page.model.ItemIcon
import com.tranphuc.home_page.model.ItemPerson
import com.tranphuc.home_page.utils.DateUtils

class PersonToItemPerson {
    fun map(person: Person): ItemPerson {
        return ItemPerson(
            person.id,
            person.name,
            DateUtils.formatLongToDate(person.birthDay * 1000),
            person.address,
            person.phone,
            person.avatar,
            createListIconBottom()
        )
    }

    private fun createListIconBottom(): MutableList<ItemIcon> {
        var listIcon: MutableList<ItemIcon> = ArrayList()
        for (i in (0..3)) {
            when (i) {
                0 -> {
                    listIcon.add(
                        ItemIcon(
                            R.drawable.ic_name_not_active,
                            R.drawable.ic_name_active

                        )
                    )
                }
                1 -> {
                    listIcon.add(
                        ItemIcon(
                            R.drawable.ic_birthday_not_active,
                            R.drawable.ic_birthday_active
                        )
                    )
                }
                2 -> {
                    listIcon.add(
                        ItemIcon(
                            R.drawable.ic_location_not_active,
                            R.drawable.ic_location_active
                        )
                    )
                }
                3 -> {
                    listIcon.add(
                        ItemIcon(
                            R.drawable.ic_phone_not_active,
                            R.drawable.ic_phone_active
                        )
                    )
                }
            }
        }
        return listIcon
    }
}