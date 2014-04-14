/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public final class MorphologicalAnalysisResult implements Serializable {
	private static final long serialVersionUID = 3598477807346909697L;

	@Nonnull
	private final String language;

	@Nonnull
	private final String stem;

	@Nullable
	private final PartOfSpeech partOfSpeech;

	@Nonnull
	private final Set<GrammaticalCategory> categories = new HashSet<>();

	/**
	 * Certain engines (like <a href = "http://hunspell.sourceforge.net/">Hunspell</a>)
	 * may lack affix dictionaries necessary to provide morphological
	 * information beyond that available about the stemming of the word.
	 *
	 * @param language lowercase 2 to 8 language code.
	 * @param stem
	 */
	public MorphologicalAnalysisResult(@Nonnull final String language,
			@Nonnull final String stem) {
		this(language, stem, null);
	}

	/**
	 * @param language lowercase 2 to 8 language code.
	 * @param stem
	 * @param partOfSpeech
	 * @param categories
	 */
	public MorphologicalAnalysisResult(@Nonnull final String language,
			@Nonnull final String stem,
			@Nullable final PartOfSpeech partOfSpeech,
			@Nonnull final GrammaticalCategory ... categories) {
		if (language == null || language.length() == 0) {
			throw new IllegalArgumentException("Language is empty");
		}
		if (stem == null || stem.length() == 0) {
			throw new IllegalArgumentException("Stem is empty");
		}

		this.language = language;
		this.stem = stem;
		this.partOfSpeech = partOfSpeech;
		this.categories.addAll(asList(categories));
	}

	public String getLanguage() {
		return this.language;
	}

	public String getStem() {
		return this.stem;
	}

	public PartOfSpeech getPartOfSpeech() {
		return this.partOfSpeech;
	}

	public Set<? extends GrammaticalCategory> getCategories() {
		return unmodifiableSet(this.categories);
	}
}
