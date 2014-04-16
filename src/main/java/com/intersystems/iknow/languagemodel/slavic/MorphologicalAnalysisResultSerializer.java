/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic;

import java.lang.reflect.Type;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public final class MorphologicalAnalysisResultSerializer implements JsonSerializer<MorphologicalAnalysisResult> {
	/**
	 * @see JsonSerializer#serialize(Object, Type, JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(final MorphologicalAnalysisResult src, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("language", src.getLanguage());
		jsonObject.addProperty("stem", src.getStem());
		final PartOfSpeech partOfSpeech = src.getPartOfSpeech();
		if (partOfSpeech != null) {
			jsonObject.addProperty("partOfSpeech", partOfSpeech.toString());
		}
		final Set<? extends GrammaticalCategory> categories = src.getCategories();
		if (!categories.isEmpty()) {
			final JsonArray serializedCategories = new JsonArray();
			for (final GrammaticalCategory category : categories) {
				serializedCategories.add(new JsonPrimitive(category.toString()));
			}
			jsonObject.add("categories", serializedCategories);
		}
		return jsonObject;
	}
}
