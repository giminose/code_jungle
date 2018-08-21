@RequestMapping(path={"/test/{service}/**"}, method = {RequestMethod.GET}, produces = {"application/json", "application/xml"})
public @ResponseBody
ResponseMessage<Map> serviceTest(@PathVariable String service, HttpServletRequest request) {
    String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    String[] values = restOfTheUrl.split("/");
    List<String> restValues = new ArrayList<>();
    for (String value : values) {
        if (!StringUtils.isEmpty(value) && !value.equals("test") && !value.equals(service)) {
            restValues.add(value);
        }
    }
    Map<String, Object> result = new HashMap<>();
    result.put("service", service);
    result.put("rest", restValues);
    ResponseMessage<Map> response = new ResponseMessage<>();
    response.setSuccess(true);
    response.getData().add(result);
    return response;
}
