import random

def get_total_dict(result_list):
  total_dict = {}
  for result in result_list:
    for word, tag in result:
      if tag in total_dict:
        total_dict[tag].append(word)
      else:
        total_dict[tag] = [word]
  return total_dict

def get_tag_dict(result):
  tag_dict = {}
  for word, tag in result:
    if tag in tag_dict:
      tag_dict[tag].append(word)
    else:
      tag_dict[tag] = [word]
  return tag_dict

def get_sentence(result):
  sentence = ""
  for word, tag in result:
    sentence += " " + word
  return sentence

def get_question_json(title, content, category, comment, choice_list):
  question_dict = {}
  question_dict['title'] = title
  question_dict['content'] = content
  question_dict['commentary'] = {}
  question_dict['commentary']['comment'] = comment
  question_dict['category'] = category
  question_dict['choiceList'] = []
  random.shuffle(choice_list)
  for choice, state in choice_list:
    choice_dict = {}
    choice_dict['content'] = choice;
    choice_dict['state'] = state
    question_dict['choiceList'].append(choice_dict)
  return question_dict

def blank(key, result):
  title = '다음 빈칸에 알맞은 답은 무엇인가?'
  category = 'BLANK'
  choice_list = []
  comment = get_sentence(result)
  tag_dict = get_tag_dict(result)

  answer = tag_dict[key][0]
  choice_list.append([answer, 'ANSWER'])
  content = comment.replace(answer, '{ }')
  return get_question_json(title, content, category, comment, choice_list)

def multiple(key, result, total_dict):
  title = '다음 설명에 가장 알맞은 보기는 무엇인가?'
  category = 'MULTIPLE'
  choice_list = []
  comment = get_sentence(result)
  tag_dict = get_tag_dict(result)
  answer = tag_dict[key][0]
  content =  comment.replace(answer, '{ }')
  choice_list.append([answer, 'ANSWER'])
  for word in total_dict[key]:
    if word != answer:
      choice_list.append([word, 'WRONG'])
    if len(choice_list) == 4:
      break
  return get_question_json(title, content, category, comment, choice_list)

def order(result_list):
  title = '다음 사건의 순서로 알맞은 보기는 무엇인가?'
  category = 'ORDER'
  comment = ""
  for result in result_list:
    comment += get_sentence(result) + "\n"
  result_list = get_processed_result_list(result_list)
  answer_list, content = get_answer_list_and_content(result_list)

  multiple_list = get_multiple_list(answer_list)
  choice_list = get_choice_list(multiple_list)
  return get_question_json(title, content, category, comment, choice_list)

def get_answer(item):
  answer = ""
  for order in item:
    answer += order + "->"
  return answer[:-2]

def get_choice_list(multiple_list):
  choice_list = []
  for i, item in enumerate(multiple_list):
    if i == 0:
      choice_list.append([get_answer(item), 'ANSWER'])
    else:
      choice_list.append([get_answer(item), 'WRONG'])
  random.shuffle(choice_list)
  return choice_list

def get_multiple_list(answer):
  choice_list = [answer[:]]
  while True:
    random.shuffle(answer)
    if answer not in choice_list:
      choice_list.append(answer[:])
    if len(choice_list) >= 4:
      break
  return choice_list

def get_processed_result_list(result_list):
  choice_list = []
  for i, result in enumerate(result_list):
    if result:
      choice_list.append(get_order_sentence(result, "&&" + str(i)))
  return sorted(choice_list)

def get_content(content_list):
  content = ""
  for index, case in sorted(content_list, key = lambda x: x[0][-1]):
    content += index + '. ' + case + '\n'
  return content

def get_answer_list_and_content(choice_list):
  answer, content_list = [], []
  for time, case in choice_list:
    item, index = time.split('&&')
    content_list.append([index, case])
    if int(index) < len(choice_list):
      answer.append(index)
  return answer, get_content(content_list)

def get_order_sentence(result, index):
  sentence, choice = "", ""
  ban_tag = ['DAT_I', 'DAT_B']
  for word, tag in result:
    if tag not in ban_tag:
      sentence += " " + word
    else:
      choice += " " + word
  return choice.strip() + index, sentence.strip()