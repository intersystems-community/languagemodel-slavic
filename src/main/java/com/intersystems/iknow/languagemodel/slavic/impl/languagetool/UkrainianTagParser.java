/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl.languagetool;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Set;

import com.intersystems.iknow.languagemodel.slavic.GrammaticalCategory;
import com.intersystems.iknow.languagemodel.slavic.PartOfSpeech;
import com.intersystems.iknow.languagemodel.slavic.categories.Declension;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalAspect;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalCase;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalGender;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalNumber;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalPerson;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalTense;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 * @see <a href = "https://github.com/languagetool-org/languagetool/blob/master/languagetool-language-modules/uk/src/main/resources/org/languagetool/resource/uk/tagset.txt">tagset.txt</a>
 * @see <a href = "https://github.com/languagetool-org/languagetool/blob/master/languagetool-language-modules/uk/src/main/resources/org/languagetool/resource/uk/ukrainian_tags.txt">ukrainian_tags.txt</a>
 */
public final class UkrainianTagParser implements TagParser {
	/**
	 * @see TagParser#parse(String)
	 */
	@Override
	public TagParseResult parse(final String tags) {
		if (tags == null || tags.length() == 0) {
			throw new IllegalArgumentException();
		}

		PartOfSpeech partOfSpeech = null;
		final Set<GrammaticalCategory> categories = new HashSet<>();

		for (final String tag : new HashSet<>(asList(tags.split("\\:")))) {
			switch (tag) {
			case "1":
				categories.add(GrammaticalPerson.FIRST);
				break;
			case "2":
				categories.add(GrammaticalPerson.SECOND);
				break;
			case "3":
				categories.add(GrammaticalPerson.THIRD);
				break;
			case "adj":
				/*
				 * Currently, participles are reported as
				 * adjectives.
				 */
				partOfSpeech = PartOfSpeech.ADJECTIVE;
				break;
			case "adv":
				partOfSpeech = PartOfSpeech.ADVERB;
				break;
			case "bad":
				/*
				 * Applies to adjectives, adverbs, verbs,
				 * transgressives, nouns and numerals.
				 */
				break;
			case "compb":
				/*
				 * Порівняльна форма.
				 * Applies to adjectives and adverbs.
				 */
				break;
			case "compr":
				/*
				 * Порівняльна форма. Applies to adjectives.
				 */
				break;
			case "supr":
				/*
				 * Найвища форма. Currently not used in the tagset.
				 */
				break;
			case "conj":
				partOfSpeech = PartOfSpeech.CONJUNCTION;
				break;
			case "conn":
//				partOfSpeech = ???;
				break;
			case "dav":
//				partOfSpeech = ???;
				break;
			case "dieprysl":
				partOfSpeech = PartOfSpeech.TRANSGRESSIVE;
				break;
			case "excl":
				partOfSpeech = PartOfSpeech.INTERJECTION;
				break;
			case "m":
				categories.add(GrammaticalGender.MASCULINE);
				break;
			case "f":
				categories.add(GrammaticalGender.FEMININE);
				break;
			case "n":
				categories.add(GrammaticalGender.NEUTER);
				break;
			case "futr":
				categories.add(GrammaticalTense.FUTURE);
				break;
			case "imper":
			case "impr":
				categories.add(GrammaticalTense.IMPERATIVE);
				break;
			case "inf":
				categories.add(GrammaticalTense.INFINITIVE);
				break;
			case "past":
				categories.add(GrammaticalTense.PAST);
				break;
			case "pres":
				categories.add(GrammaticalTense.PRESENT);
				break;
			case "imperf":
				/*
				 * Applies to transgressives.
				 */
				categories.add(GrammaticalAspect.IMPERFECTIVE);
				break;
			case "perf":
				/*
				 * Applies to transgressives.
				 */
				categories.add(GrammaticalAspect.PERFECTIVE);
				break;
			case "impers":
				/*
				 * Безособова форма дієслова.
				 */
			case "verb":
				partOfSpeech = PartOfSpeech.VERB;
				break;
			case "insert_sl":
				/*
				 * Вставне слово.
				 */
//				partOfSpeech = ???;
				break;
			case "noun":
				partOfSpeech = PartOfSpeech.NOUN;
				break;
			case "numr":
				partOfSpeech = PartOfSpeech.NUMERAL;
				break;
			case "nv":
				categories.add(Declension.NONE);
				break;
			case "s":
				categories.add(GrammaticalNumber.SINGLE);
				break;
			case "p":
				/*
				 * Applies to adjectives, nouns, numerals,
				 * pronouns, verbs.
				 */
				categories.add(GrammaticalNumber.PLURAL);
				break;
			case "pl":
				/*
				 * Applies to nouns.
				 */
				break;
			case "part":
				partOfSpeech = PartOfSpeech.PARTICLE;
				break;
			case "pron":
				partOfSpeech = PartOfSpeech.PRONOUN;
				break;
			case "pruim":
			case "pryim":
				partOfSpeech = PartOfSpeech.PREPOSITION;
				break;
			case "prusudk_sl":
			case "prusydk_sl":
			case "prydusk_sl":
			case "prysduk_sl":
			case "prysudk_sl":
			case "prysydk_sl":
				/*
				 * Присудкове слово.
				 */
//				partOfSpeech = ???;
				break;
			case "rev":
				/*
				 * Зворотна форма (дієслова).
				 * Applies to verbs and transgressives.
				 */
				break;
			case "rv_naz":
				/*
				 * Потребує називного відмінку.
				 */
				break;
			case "rv_rod":
				/*
				 * Потребує родового відмінку.
				 */
				break;
			case "rv_dav":
				/*
				 * Потребує давального відмінку.
				 */
				break;
			case "rv_zna":
				/*
				 * Потребує знахідного відмінку.
				 */
				break;
			case "rv_oru":
				/*
				 * Потребує одрудного відмінку.
				 */
				break;
			case "rv_mis":
				/*
				 * Потребує місцевого відмінку.
				 */
				break;
			case "rv_kly":
				/*
				 * Потребує кличного відмінку.
				 */
				break;
			case "todo":
//				partOfSpeech = ???;
				break;
			case "v-u":
				/*
				 * Applies to adjectives, adverbs,
				 * transgressives, nouns, verbs.
				 */
				break;
			case "v_naz":
				categories.add(GrammaticalCase.NOMINATIVE);
				break;
			case "v_rod":
				categories.add(GrammaticalCase.GENITIVE);
				break;
			case "v_dav":
				categories.add(GrammaticalCase.DATIVE);
				break;
			case "v_zna":
				categories.add(GrammaticalCase.ACCUSATIVE);
				break;
			case "v_oru":
				categories.add(GrammaticalCase.INSTRUMENTAL);
				break;
			case "v_mis":
				categories.add(GrammaticalCase.PREPOSITIONAL);
				break;
			case "v_kly":
				categories.add(GrammaticalCase.VOCATIVE);
				break;
			default:
				break;
			}
		}
		return new TagParseResult(partOfSpeech, categories);
	}
}
