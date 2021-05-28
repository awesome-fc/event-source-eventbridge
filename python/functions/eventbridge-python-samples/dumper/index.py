# -*- coding: utf-8 -*-
import logging
import json

# Event schema from Alibaba Cloud EventBridge service
# https://help.aliyun.com/document_detail/181428.htm#concept-1938024

def handler(event, context):
  logger = logging.getLogger()
  # Event is bytes so deserialize it to json
  jsonEvent = json.loads(event)
  logger.info('event[id] = %s' % (jsonEvent['id']))

  # Access data field
  data = jsonEvent['data']
  
  # The return value does not matter.
  return 'success'