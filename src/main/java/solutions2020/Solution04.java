package solutions2020;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class Solution04 {

  private final static Map<String, Function<String, Boolean>> FIELD_VALIDATORS = new HashMap<String, Function<String, Boolean>>() {
    {
      put("byr", Solution04::isByrValid);
      put("iyr", Solution04::isIyrValid);
      put("eyr", Solution04::isEyrValid);
      put("hgt", Solution04::isHgtValid);
      put("hcl", Solution04::isHclValid);
      put("ecl", Solution04::isEclValid);
      put("pid", Solution04::isPidValid);
    }
  };

  private final static List<String> VALID_EYES = Lists.newArrayList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

  public static int partOne(Stream<String> lines) {
    Set<String> fields = new HashSet<>();
    AtomicInteger validPassports = new AtomicInteger();
    lines.forEach(line -> {
      if (StringUtils.isEmpty(line)) {
        if (Sets.intersection(fields, FIELD_VALIDATORS.keySet()).size() == 7) {
          validPassports.addAndGet(1);
        }
        fields.clear();
      } else {
        Matcher matcher = Pattern.compile("([a-zA-Z]*?):([^ ]*)").matcher(line);
        while (matcher.find()) {
          fields.add(matcher.group(1));
        }
      }
    });
    return validPassports.get();
  }

  public static int partTwo(Stream<String> lines) {
    Set<String> fields = new HashSet<>();
    AtomicInteger validPassports = new AtomicInteger();
    AtomicBoolean valid = new AtomicBoolean(true);
    lines.forEach(line -> {
      if (StringUtils.isEmpty(line)) {
        if (valid.get() && Sets.intersection(fields, FIELD_VALIDATORS.keySet()).size() == 7) {
          validPassports.addAndGet(1);
        }
        fields.clear();
        valid.set(true);
      } else if (valid.get()) {
        Matcher matcher = Pattern.compile("([a-zA-Z]*?):([^ ]*)").matcher(line);
        while (matcher.find()) {
          String field = matcher.group(1);
          if (FIELD_VALIDATORS.get(field) == null || FIELD_VALIDATORS.get(field).apply(matcher.group(2))) {
            fields.add(field);
          } else {
            valid.set(false);
          }
        }
      }
    });
    if (valid.get() && Sets.intersection(fields, FIELD_VALIDATORS.keySet()).size() == 7) {
      validPassports.addAndGet(1);
    }
    return validPassports.get();
  }

  private static boolean isByrValid(String byr) {
    return 1920 <= Integer.parseInt(byr) && Integer.parseInt(byr) <= 2002;
  }

  private static boolean isIyrValid(String iyr) {
    return 2010 <= Integer.parseInt(iyr) && Integer.parseInt(iyr) <= 2020;
  }

  private static boolean isEyrValid(String eyr) {
    return 2020 <= Integer.parseInt(eyr) && Integer.parseInt(eyr) <= 2030;
  }

  private static boolean isHgtValid(String hgt) {
    Matcher matcher = Pattern.compile("^(\\d+)(cm|in)$").matcher(hgt);
    if (matcher.find()) {
      int height = Integer.parseInt(matcher.group(1));
      if (matcher.group(2).equals("cm")) {
        return 150 <= height && height <= 193;
      } else if (matcher.group(2).endsWith("in")) {
        return 59 <= height && height <= 76;
      }
    }
    return false;
  }

  private static boolean isHclValid(String hcl) {
    return Pattern.compile("^#[a-f0-9]{6}$").matcher(hcl).find();
  }

  private static boolean isEclValid(String ecl) {
    return VALID_EYES.contains(ecl);
  }

  private static boolean isPidValid(String pid) {
    return Pattern.compile("^[0-9]{9}$").matcher(pid).find();
  }

}
