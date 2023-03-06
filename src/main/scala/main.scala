package be.adamv.dejson

import java.util
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.saasquatch.jsonschemainferrer.*

import java.time.{DayOfWeek, Month}


val mapper = ObjectMapper()
val inferrer = JsonSchemaInferrer
  .newBuilder
  .setSpecVersion(SpecVersion.DRAFT_06)
  .addFormatInferrers(FormatInferrers.email, FormatInferrers.ip)
  .setAdditionalPropertiesPolicy(AdditionalPropertiesPolicies.notAllowed)
  .setRequiredPolicy(RequiredPolicies.nonNullCommonFields)
  .addEnumExtractors(EnumExtractors.validEnum(classOf[Month]), EnumExtractors.validEnum(classOf[DayOfWeek]))
  .build

@main def example =
  val sample1 = mapper.readTree("{\"🙈\":\"https://saasquatch.com\",\"🙉\":[-1.5,2,\"hello@saasquat.ch\",false],\"🙊\":3,\"weekdays\":[\"MONDAY\",\"TUESDAY\"]}")
  val sample2 = mapper.readTree("{\"🙈\":1,\"🙉\":{\"🐒\":true,\"🐵\":[2,\"1234:5678::\"],\"🍌\":null},\"🙊\":null,\"months\":[\"JANUARY\",\"FEBRUARY\"]}")
  val resultForSample1 = inferrer.inferForSample(sample1)
  val resultForSample1And2 = inferrer.inferForSamples(util.Arrays.asList(sample1, sample2))
  for (j <- List(sample1, sample2, resultForSample1, resultForSample1And2)) do
    println(mapper.writeValueAsString(j))
