package com.oszero.deliver.business.admin.constant;

/**
 * @author oszero
 * @version 1.0.2
 */
public interface UserMenuConstant {
    String USER_MENU_ADMIN = """
            {
              groupManage: [
                {
                  key: 'MC',
                  icon: 'MessageOutlined',
                  label: '模版配置',
                  children: [
                    {
                      key: '/groupManage/template',
                      label: '消息模板配置'
                    }
                  ]
                },
                {
                  key: 'AC',
                  icon: 'AppstoreOutlined',
                  label: '应用配置',
                  children: [
                    {
                      key: '/groupManage/app',
                      label: '渠道应用配置'
                    }
                  ]
                },
                {
                  key: 'FM',
                  icon: 'FileTextOutlined',
                  label: '文件管理',
                  children: [
                    {
                      key: '/groupManage/file',
                      label: '平台文件管理'
                    }
                  ]
                },
                {
                  key: 'TM',
                  icon: 'SoundOutlined',
                  label: '群发任务',
                  children: [
                    {
                      key: '/groupManage/task',
                      label: '群发任务配置'
                    },
                    {
                      key: '/groupManage/peopleGroup',
                      label: '人群模版配置'
                    }
                  ]
                }
              ],
              systemManage: [
                {
                  key: '/systemManage/myAccount',
                  icon: 'UserOutlined',
                  label: '我的账户'
                },
                {
                  key: '/systemManage/sentinel',
                  icon: 'DashboardOutlined',
                  label: 'Sentinel控制台'
                }
              ]
            }
            """;
    String USER_MENU_ORDINARY = """
            {
              groupManage: [
                {
                  key: 'MC',
                  icon: 'MessageOutlined',
                  label: '模版配置',
                  children: [
                    {
                      key: '/groupManage/template',
                      label: '消息模板配置'
                    }
                  ]
                },
                {
                  key: 'AC',
                  icon: 'AppstoreOutlined',
                  label: '应用配置',
                  children: [
                    {
                      key: '/groupManage/app',
                      label: '渠道应用配置'
                    }
                  ]
                },
                {
                  key: 'FM',
                  icon: 'FileTextOutlined',
                  label: '文件管理',
                  children: [
                    {
                      key: '/groupManage/file',
                      label: '平台文件管理'
                    }
                  ]
                },
                {
                  key: 'TM',
                  icon: 'SoundOutlined',
                  label: '群发任务',
                  children: [
                    {
                      key: '/groupManage/task',
                      label: '群发任务配置'
                    },
                    {
                      key: '/groupManage/peopleGroup',
                      label: '人群模版配置'
                    }
                  ]
                }
              ],
              systemManage: [
                {
                  key: '/systemManage/myAccount',
                  icon: 'UserOutlined',
                  label: '我的账户'
                }
              ]
            }
            """;
}
