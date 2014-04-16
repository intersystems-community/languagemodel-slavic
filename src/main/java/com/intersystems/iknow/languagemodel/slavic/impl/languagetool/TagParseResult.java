/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl.languagetool;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.intersystems.iknow.languagemodel.slavic.GrammaticalCategory;
import com.intersystems.iknow.languagemodel.slavic.PartOfSpeech;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public final class TagParseResult {
	@Nullable
	private final PartOfSpeech partOfSpeech;

	@Nonnull
	private final Set<GrammaticalCategory> categories = new HashSet<>();

	/**
	 * @param partOfSpeech
	 * @param categories
	 */
	TagParseResult(@Nullable final PartOfSpeech partOfSpeech,
			@Nonnull final GrammaticalCategory ... categories) {
		this(partOfSpeech, asList(categories));
	}

	/**
	 * @param partOfSpeech
	 * @param categories
	 */
	TagParseResult(@Nullable final PartOfSpeech partOfSpeech,
			@Nonnull final Collection<GrammaticalCategory> categories) {
		this.partOfSpeech = partOfSpeech;
		this.categories.addAll(categories);
	}

	public PartOfSpeech getPartOfSpeech() {
		return this.partOfSpeech;
	}

	public Set<GrammaticalCategory> getCategories() {
		return unmodifiableSet(this.categories);
	}
}
