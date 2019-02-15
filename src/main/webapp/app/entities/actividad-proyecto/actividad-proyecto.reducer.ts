import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IActividadProyecto, defaultValue } from 'app/shared/model/actividad-proyecto.model';

export const ACTION_TYPES = {
  FETCH_ACTIVIDADPROYECTO_LIST: 'actividadProyecto/FETCH_ACTIVIDADPROYECTO_LIST',
  FETCH_ACTIVIDADPROYECTO: 'actividadProyecto/FETCH_ACTIVIDADPROYECTO',
  CREATE_ACTIVIDADPROYECTO: 'actividadProyecto/CREATE_ACTIVIDADPROYECTO',
  UPDATE_ACTIVIDADPROYECTO: 'actividadProyecto/UPDATE_ACTIVIDADPROYECTO',
  DELETE_ACTIVIDADPROYECTO: 'actividadProyecto/DELETE_ACTIVIDADPROYECTO',
  RESET: 'actividadProyecto/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IActividadProyecto>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ActividadProyectoState = Readonly<typeof initialState>;

// Reducer

export default (state: ActividadProyectoState = initialState, action): ActividadProyectoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACTIVIDADPROYECTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACTIVIDADPROYECTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ACTIVIDADPROYECTO):
    case REQUEST(ACTION_TYPES.UPDATE_ACTIVIDADPROYECTO):
    case REQUEST(ACTION_TYPES.DELETE_ACTIVIDADPROYECTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ACTIVIDADPROYECTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACTIVIDADPROYECTO):
    case FAILURE(ACTION_TYPES.CREATE_ACTIVIDADPROYECTO):
    case FAILURE(ACTION_TYPES.UPDATE_ACTIVIDADPROYECTO):
    case FAILURE(ACTION_TYPES.DELETE_ACTIVIDADPROYECTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACTIVIDADPROYECTO_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACTIVIDADPROYECTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACTIVIDADPROYECTO):
    case SUCCESS(ACTION_TYPES.UPDATE_ACTIVIDADPROYECTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACTIVIDADPROYECTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/actividad-proyectos';

// Actions

export const getEntities: ICrudGetAllAction<IActividadProyecto> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ACTIVIDADPROYECTO_LIST,
    payload: axios.get<IActividadProyecto>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IActividadProyecto> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACTIVIDADPROYECTO,
    payload: axios.get<IActividadProyecto>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IActividadProyecto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACTIVIDADPROYECTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IActividadProyecto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACTIVIDADPROYECTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IActividadProyecto> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACTIVIDADPROYECTO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
