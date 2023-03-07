package be.adamv.dejson

import java.util
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.saasquatch.jsonschemainferrer.*

import java.io.File
import java.time.{DayOfWeek, Month}


val mapper = ObjectMapper()
val inferrer = JsonSchemaInferrer
  .newBuilder
  .setSpecVersion(SpecVersion.DRAFT_06)
  .addFormatInferrers(FormatInferrers.email, FormatInferrers.dateTime, FormatInferrers.ip)
  .setAdditionalPropertiesPolicy(AdditionalPropertiesPolicies.notAllowed)
  .setExamplesPolicy(ExamplesPolicies.useFirstSamples(3))
  .setRequiredPolicy(RequiredPolicies.nonNullCommonFields)
  .addEnumExtractors(EnumExtractors.validEnum(classOf[Month]), EnumExtractors.validEnum(classOf[DayOfWeek]))
  .setArrayLengthFeatures(util.EnumSet.allOf(classOf[ArrayLengthFeature]))
  .build

@main def example(filename: String) =
  val f = new File(filename)
  val contents = mapper.readTree(f)
  val result = inferrer.inferForSample(contents)
  println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result))
