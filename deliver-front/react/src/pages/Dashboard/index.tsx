import React, {useEffect, useState} from 'react';
import {Column} from '@ant-design/charts';
import {Pie} from '@ant-design/plots';
import {PageContainer,} from '@ant-design/pro-components';
import {Card, Col, message, Radio, Row} from 'antd';
import {AppstoreTwoTone, FileTwoTone, FunnelPlotTwoTone, MessageTwoTone} from '@ant-design/icons';
import {getDashboardHeadData} from "@/services/ant-design-pro/api";

const Page: React.FC = () => {

  const [dashboardHeadData, setDashboardHeadData] = useState<API.DashboardHeadData>({
    numberOfMessagesToday: 101,
    numberOfPlatformFiles: 120,
    accumulatedTemplateOwnership: 20,
    numberOfApps: 141,
  })

  useEffect(() => {
    // 定义一个异步函数，用于获取数据并更新状态
    const fetchData = async () => {
      try {
        // 调用接口获取数据
        const response = await getDashboardHeadData();
        // 更新状态
        setDashboardHeadData(response);
      } catch (error) {
        message.error(error.message)
        console.error('Error fetching dashboard data:', error);
      }
    }
    fetchData(); // 调用数据获取函数
  }, [])

  const messageInfo = [
    {
      name: '成功',
      时间: 'Week1',
      次数: 18,
    },
    {
      name: '失败',
      时间: 'Week1',
      次数: 11,
    },
    {
      name: '成功',
      时间: 'Week2',
      次数: 200,
    },
    {
      name: '失败',
      时间: 'Week2',
      次数: 20,
    },
    {
      name: '成功',
      时间: 'Week3',
      次数: 180,
    },
    {
      name: '失败',
      时间: 'Week3',
      次数: 28,
    },
    {
      name: '成功',
      时间: 'Week4',
      次数: 18,
    },
    {
      name: '失败',
      时间: 'Week4',
      次数: 28,
    },
    {
      name: '成功',
      时间: 'Week5',
      次数: 108,
    },
    {
      name: '失败',
      时间: 'Week5',
      次数: 18,
    },
    {
      name: '成功',
      时间: 'Week6',
      次数: 14,
    },
    {
      name: '失败',
      时间: 'Week6',
      次数: 1,
    },
    {
      name: '成功',
      时间: 'Week7',
      次数: 167,
    },
    {
      name: '失败',
      时间: 'Week7',
      次数: 21,
    },
  ];

  const messageConfig = {
    data: messageInfo,
    isGroup: true,
    xField: '时间',
    yField: '次数',
    seriesField: 'name',

    /** 设置颜色 */
    color: ['#5470C6', '#A90000'],

    /** 设置间距 */
    // marginRatio: 0.1,
    label: {
      // 可手动配置 label 数据标签位置
      position: 'middle',
      // 'top', 'middle', 'bottom'
      // 可配置附加的布局方法
      layout: [
        // 柱形图数据标签位置自动调整
        {
          type: 'interval-adjust-position',
        }, // 数据标签防遮挡
        {
          type: 'interval-hide-overlap',
        }, // 数据标签文颜色自动调整
        {
          type: 'adjust-color',
        },
      ],
    },
  };

  const [templateInfo, setTemplateInfo] = useState<any>([
    {
      type: '分类一',
      value: 101,
    },
    {
      type: '分类二',
      value: 201,
    },
    {
      type: '分类三',
      value: 200,
    },
    {
      type: '分类四',
      value: 120,
    },
    {
      type: '分类五',
      value: 130,
    },
  ]);

  const templateConfig = {
    appendPadding: 10,
    data: templateInfo,
    angleField: 'value',
    colorField: 'type',
    radius: 0.75,
    label: {
      type: 'spider',
      labelHeight: 28,
      content: '{name}\n{percentage}',
    },
    interactions: [
      {
        type: 'element-selected',
      },
      {
        type: 'element-active',
      },
    ],
  };

  const appInfo = [
    {
      type: '分类一',
      value: 227,
    },
    {
      type: '分类二',
      value: 225,
    },
    {
      type: '分类三',
      value: 128,
    },
    {
      type: '分类四',
      value: 125,
    },
    {
      type: '分类五',
      value: 120,
    },
  ];
  const appConfig = {
    appendPadding: 10,
    data: appInfo,
    angleField: 'value',
    colorField: 'type',
    radius: 1,
    innerRadius: 0.6,
    label: {
      type: 'inner',
      offset: '-50%',
      content: '{value}',
      style: {
        textAlign: 'center',
        fontSize: 14,
      },
    },
    interactions: [
      {
        type: 'element-selected',
      },
      {
        type: 'element-active',
      },
    ],
    statistic: {
      title: false,
      content: {
        style: {
          whiteSpace: 'pre-wrap',
          overflow: 'hidden',
          textOverflow: 'ellipsis',
        },
        content: '',
      },
    },
  };

  const pushUserInfo = [
    {
      type: '分类一',
      value: 27,
    },
    {
      type: '分类二',
      value: 25,
    },
    {
      type: '分类三',
      value: 28,
    },
    {
      type: '分类四',
      value: 25,
    },
    {
      type: '分类五',
      value: 20,
    }
  ];
  const pushUserConfig = {
    appendPadding: 10,
    data: pushUserInfo,
    angleField: 'value',
    colorField: 'type',
    radius: 1,
    innerRadius: 0.6,
    label: {
      type: 'inner',
      offset: '-50%',
      content: '{value}',
      style: {
        textAlign: 'center',
        fontSize: 14,
      },
    },
    interactions: [
      {
        type: 'element-selected',
      },
      {
        type: 'element-active',
      },
    ],
    statistic: {
      title: false,
      content: {
        style: {
          whiteSpace: 'pre-wrap',
          overflow: 'hidden',
          textOverflow: 'ellipsis',
        },
        content: '',
      },
    },
  };
  return (
    <PageContainer>
      <div id="dashboard-container">
        <div className="dashboard-info" style={{paddingBottom: 12}}>
          <Row gutter={[12, 12]}>
            <Col span={6}>
              <Card style={{width: 281, height: 100}}>
                <span>
                  <MessageTwoTone twoToneColor="#1890FF" style={{height: 60, width: 60, fontSize: 42}}/>
                </span>
                <span style={{fontSize: 16, color: "#636262"}}>今日消息量</span>
                <span style={{fontSize: 32, color: "#000000"}}>{dashboardHeadData.numberOfMessagesToday}</span>
              </Card>
            </Col>
            <Col span={6}>
              <Card style={{width: 280, height: 100}}>
                <span>
                  <FileTwoTone twoToneColor="#1890FF" style={{height: 60, width: 60, fontSize: 42}}/>
                </span>
                <span style={{fontSize: 16, color: "#636262"}}>平台文件数</span>
                <span style={{fontSize: 32, color: "#000000"}}>{dashboardHeadData.numberOfPlatformFiles}</span>
              </Card>
            </Col>
            <Col span={6}>
              <Card style={{width: 281, height: 100}}>
                <span>
                  <FunnelPlotTwoTone twoToneColor="#1890FF" style={{height: 60, width: 60, fontSize: 42}}/>
                </span>
                <span style={{fontSize: 16, color: "#636262"}}>累计模板数</span>
                <span style={{fontSize: 32, color: "#000000"}}>{dashboardHeadData.accumulatedTemplateOwnership}</span>
              </Card>
            </Col>
            <Col span={6}>
              <Card style={{width: 280, height: 100}}>
                <span>
                  <AppstoreTwoTone twoToneColor="#1890FF" style={{height: 60, width: 60, fontSize: 42}}/>
                </span>
                <span style={{fontSize: 16, color: "#636262"}}>渠道 APP 数</span>
                <span style={{fontSize: 32, color: "#000000"}}>{dashboardHeadData.numberOfApps}</span>
              </Card>
            </Col>
          </Row>
        </div>
        <div className="dashboard-charts">
          <Row gutter={[12, 12]}>
            <Col span={12}>
              <Card title="消息详情" extra={<Radio.Group defaultValue="a" buttonStyle="solid">
                <Radio.Button value="a">今日</Radio.Button>
                <Radio.Button value="b">本周</Radio.Button>
                <Radio.Button value="c">本月</Radio.Button>
                <Radio.Button value="d">本年</Radio.Button>
              </Radio.Group>} style={{width: 580}}>
                <Column {...messageConfig} />
              </Card>
            </Col>
            <Col span={12}>
              <Card title="模板使用 TOP5" extra={<Radio.Group defaultValue="a" buttonStyle="solid">
                <Radio.Button value="a">今日</Radio.Button>
                <Radio.Button value="b">本周</Radio.Button>
                <Radio.Button value="c">本月</Radio.Button>
                <Radio.Button value="d">本年</Radio.Button>
              </Radio.Group>} style={{width: 580}}>
                <Pie {...templateConfig} />
              </Card>
            </Col>
            <Col span={12}>
              <Card title="渠道 APP 使用 TOP5" extra={<Radio.Group defaultValue="a" buttonStyle="solid">
                <Radio.Button value="a">今日</Radio.Button>
                <Radio.Button value="b">本周</Radio.Button>
                <Radio.Button value="c">本月</Radio.Button>
                <Radio.Button value="d">本年</Radio.Button>
              </Radio.Group>} style={{width: 580}}>
                <Pie {...appConfig} />
              </Card>
            </Col>
            <Col span={12}>
              <Card title="推送用户 TOP5" extra={<Radio.Group defaultValue="a" buttonStyle="solid">
                <Radio.Button value="a">今日</Radio.Button>
                <Radio.Button value="b">本周</Radio.Button>
                <Radio.Button value="c">本月</Radio.Button>
                <Radio.Button value="d">本年</Radio.Button>
              </Radio.Group>} style={{width: 580}}>
                <Pie {...pushUserConfig} />
              </Card>
            </Col>
          </Row>
        </div>
      </div>
    </PageContainer>
  );
};
export default Page;
