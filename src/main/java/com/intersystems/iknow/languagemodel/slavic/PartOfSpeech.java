/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic;

import com.intersystems.iknow.languagemodel.slavic.categories.Declension;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalAspect;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalCase;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalGender;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalNumber;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalPerson;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalTense;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public enum PartOfSpeech {
	/**
	 * Russian: имя существительное<br>
	 * Ukrainian: іменник
	 */
	@SupportedCategories({
		Declension.class,
		GrammaticalCase.class,
		GrammaticalGender.class,
		GrammaticalNumber.class,
	})
	NOUN,

	/**
	 * Russian: имя прилагательное<br>
	 * Ukrainian: прикметник
	 */
	@SupportedCategories({
		GrammaticalCase.class,
		GrammaticalGender.class,
		GrammaticalNumber.class,
	})
	ADJECTIVE,

	/**
	 * Russian: имя числительное<br>
	 * Ukrainian: числівник
	 */
	@SupportedCategories({
		GrammaticalCase.class,
		GrammaticalGender.class, /* один молоток – одна рубашка */
		GrammaticalNumber.class,
	})
	NUMERAL,

	/**
	 * Russian: местоимение<br>
	 * Ukrainian: займенник
	 */
	@SupportedCategories({
		GrammaticalCase.class,
		GrammaticalGender.class,
		GrammaticalNumber.class,
		GrammaticalPerson.class,
	})
	PRONOUN,

	/**
	 * Russian: глагол<br>
	 * Ukrainian: дієслово
	 */
	@SupportedCategories({
		GrammaticalGender.class, /* он лежал – она лежала – оно лежало */
		GrammaticalNumber.class, /* я бегу – мы бежим */
		GrammaticalPerson.class, /* я бегу – ты бежишь – он бежит */
		GrammaticalTense.class,
		GrammaticalAspect.class, /* бегу – прибегу */
	})
	VERB,

	/**
	 * Russian: наречие<br>
	 * Ukrainian: прислівник
	 */
	ADVERB,

	/**
	 * Russian: предлог<br>
	 * Ukrainian: прийменник
	 */
	PREPOSITION,

	/**
	 * Russian: союз<br>
	 * Ukrainian: сполучник
	 */
	CONJUNCTION,

	/**
	 * Russian: частица<br>
	 * Ukrainian: частка
	 */
	PARTICLE,

	/**
	 * Russian: междометие<br>
	 * Ukrainian: вигук
	 */
	INTERJECTION,

	/**
	 * Russian: причастие<br>
	 * Ukrainian: дієприкметник
	 */
	@SupportedCategories({
		/* Inherited from adjective: */
		GrammaticalCase.class,
		GrammaticalGender.class,
		GrammaticalNumber.class,
		/* Inherited from verb: */
		GrammaticalTense.class, /* бегущий – бежавший, прибежавший */
		GrammaticalAspect.class, /* бегуший, бежавший – прибежавший */
	})
	PARTICIPLE,

	/**
	 * Russian: деепричастие<br>
	 * Ukrainian: дієприслівник
	 */
	@SupportedCategories({
		GrammaticalTense.class, /* умываясь – умывшись */
		GrammaticalAspect.class, /* бежав – прибежав (прибежавши) */
	})
	TRANSGRESSIVE,
}
