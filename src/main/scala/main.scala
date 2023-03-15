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
  .setAdditionalPropertiesPolicy(AdditionalPropertiesPolicies.noOp())
  .setExamplesPolicy(ExamplesPolicies.useFirstSamples(3))
  .setRequiredPolicy(RequiredPolicies.nonNullCommonFields)
  .addEnumExtractors(EnumExtractors.validEnum(classOf[Month]), EnumExtractors.validEnum(classOf[DayOfWeek]))
  .setArrayLengthFeatures(util.EnumSet.allOf(classOf[ArrayLengthFeature]))
  .build

@main def example(filename: String, as_multiple: Boolean) =
  val f = new File(filename)
  val contents = mapper.readTree(f)
  val result =
    if as_multiple && contents.isArray then
      val list: java.util.ArrayList[JsonNode] = new java.util.ArrayList()
      contents.elements().forEachRemaining(list.add(_))
      inferrer.inferForSamples(list)
    else inferrer.inferForSample(contents)
  println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result))
