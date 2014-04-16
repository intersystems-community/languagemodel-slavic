/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

import java.io.Serializable;
import java.util.Collection;
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
		this(language, stem, partOfSpeech, asList(categories));
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
			@Nonnull final Collection<GrammaticalCategory> categories) {
		if (language == null || language.length() == 0) {
			throw new IllegalArgumentException("Language is empty");
		}
		if (stem == null || stem.length() == 0) {
			throw new IllegalArgumentException("Stem is empty");
		}

		this.language = language;
		this.stem = stem;
		this.partOfSpeech = partOfSpeech;
		this.categories.addAll(categories);
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

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append(String.format(":language %s :stem %s", this.language, this.stem));
		if (this.partOfSpeech != null) {
			builder.append(String.format(" :partOfSpeech %s", this.partOfSpeech));
		}
		if (!this.categories.isEmpty()) {
			builder.append(String.format(" :categories %s", this.categories));
		}
		builder.append('}');
		return builder.toString();
	}

	/**
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.categories.hashCode();
		result = prime * result + this.language.hashCode();
		result = prime * result + (this.partOfSpeech == null ? 0 : this.partOfSpeech.hashCode());
		result = prime * result + this.stem.hashCode();
		return result;
	}

	/**
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof MorphologicalAnalysisResult) {
			final MorphologicalAnalysisResult that = (MorphologicalAnalysisResult) obj;
			return this.language.equals(that.language)
					&& this.stem.equals(that.stem)
					&& this.partOfSpeech == that.partOfSpeech
					&& this.categories.equals(that.categories);
		}
		return false;
	}
}
