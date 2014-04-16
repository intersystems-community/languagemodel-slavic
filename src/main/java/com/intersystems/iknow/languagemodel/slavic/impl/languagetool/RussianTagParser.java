/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl.languagetool;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Set;

import com.intersystems.iknow.languagemodel.slavic.GrammaticalCategory;
import com.intersystems.iknow.languagemodel.slavic.PartOfSpeech;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalCase;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalGender;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalNumber;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalPerson;
import com.intersystems.iknow.languagemodel.slavic.categories.GrammaticalTense;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 * @see <a href = "https://github.com/languagetool-org/languagetool/blob/master/languagetool-language-modules/ru/src/main/resources/org/languagetool/resource/ru/tagset.txt">tagset.txt</a>
 * @see <a href = "https://github.com/languagetool-org/languagetool/blob/master/languagetool-language-modules/ru/src/main/resources/org/languagetool/resource/ru/tags_russian.txt">tags_russian.txt</a>
 */
public final class RussianTagParser implements TagParser {
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
			case "ABR":
			case "Misc":
			case "PARENTHESIS":
			case "PRDC":
//				partOfSpeech = ???;
				break;
			case "ADV":
				partOfSpeech = PartOfSpeech.ADVERB;
				break;
			case "ADJ":
			case "ADJ_Short":
			case "PADJ":
			case "ADJ_S":
			case "ADJ_Comp":
				partOfSpeech = PartOfSpeech.ADJECTIVE;
				break;
			case "PREP":
				partOfSpeech = PartOfSpeech.PREPOSITION;
				break;
			case "CONJ":
				partOfSpeech = PartOfSpeech.CONJUNCTION;
				break;
			case "INTERJECTION":
				partOfSpeech = PartOfSpeech.INTERJECTION;
				break;
			case "PARTICLE":
				partOfSpeech = PartOfSpeech.PARTICLE;
				break;
			case "Num":
			case "NumC":
			case "Ord":
				partOfSpeech = PartOfSpeech.NUMERAL;
				break;
			case "NN":
			case "NNN":
			case "NNP":
			case "NNF":
				partOfSpeech = PartOfSpeech.NOUN;
				break;
			case "PNN":
				partOfSpeech = PartOfSpeech.PRONOUN;
				break;
			case "VB":
				partOfSpeech = PartOfSpeech.VERB;
				break;
			case "DPT":
				partOfSpeech = PartOfSpeech.TRANSGRESSIVE;
				break;
			case "PT":
			case "PT_Short":
				partOfSpeech = PartOfSpeech.PARTICIPLE;
				break;
			case "Masc":
				categories.add(GrammaticalGender.MASCULINE);
				break;
			case "Fem":
				categories.add(GrammaticalGender.FEMININE);
				break;
			case "Neut":
				categories.add(GrammaticalGender.NEUTER);
				break;
			case "Sin":
				categories.add(GrammaticalNumber.SINGLE);
				break;
			case "PL":
				categories.add(GrammaticalNumber.PLURAL);
				break;
			case "Nom":
				categories.add(GrammaticalCase.NOMINATIVE);
				break;
			case "R":
				categories.add(GrammaticalCase.GENITIVE);
				break;
			case "D":
				categories.add(GrammaticalCase.DATIVE);
				break;
			case "V":
				categories.add(GrammaticalCase.ACCUSATIVE);
				break;
			case "T":
				categories.add(GrammaticalCase.INSTRUMENTAL);
				break;
			case "P":
				categories.add(GrammaticalCase.PREPOSITIONAL);
				break;
			case "P1":
				categories.add(GrammaticalPerson.FIRST);
				break;
			case "P2":
				categories.add(GrammaticalPerson.SECOND);
				break;
			case "P3":
				categories.add(GrammaticalPerson.THIRD);
				break;
			case "INF":
				categories.add(GrammaticalTense.INFINITIVE);
				break;
			case "Past":
				categories.add(GrammaticalTense.PAST);
				break;
			case "Real":
				categories.add(GrammaticalTense.PRESENT);
				break;
			case "Fut":
				categories.add(GrammaticalTense.FUTURE);
				break;
			case "IMP":
				categories.add(GrammaticalTense.IMPERATIVE);
				break;
			default:
				break;
			}
		}
		return new TagParseResult(partOfSpeech, categories);
	}
}
